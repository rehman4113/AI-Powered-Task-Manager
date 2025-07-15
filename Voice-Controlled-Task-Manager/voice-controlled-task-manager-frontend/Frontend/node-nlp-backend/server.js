const express = require('express');
const { NlpManager } = require('node-nlp');
const cors = require('cors');
const path = require('path');

const app = express();
const port = 3001; // This is the port your Node.js NLP backend will run on

// Middleware
app.use(express.json()); // To parse JSON request bodies
app.use(cors()); // Enable CORS for your frontend

// Load the trained NLP model
let manager;
const modelPath = path.join(__dirname, 'model.nlp');

async function loadModel() {
    try {
        manager = new NlpManager({ languages: ['en'] });
        manager.load(modelPath);
        console.log('Node-NLP model loaded successfully!');
    } catch (error) {
        console.error('Failed to load Node-NLP model:', error);
        // In a production app, you might want more robust error handling
        // process.exit(1); // Exit if critical model cannot be loaded
    }
}

// Define the API endpoint for your frontend to send commands
app.post('/api/process-command', async (req, res) => {
    const { command } = req.body;

    if (!manager) {
        return res.status(503).json({ error: 'Node-NLP model not loaded or failed to load.' });
    }
    if (!command) {
        return res.status(400).json({ error: 'Command not provided in request body.' });
    }

    try {
        const result = await manager.process('en', command);
        console.log('Node-NLP processing result:', result); // For debugging

        let taskDescription = '';

        // Basic entity extraction/task identification based on intent
        if (result.intent === 'AddTask') {
            const addTaskMatch = command.match(/(?:add(?: a)? task(?: called)?|i want to add|new task:|remind me to|please remind me to)\s*(.+)/i);
            if (addTaskMatch && addTaskMatch[1]) {
                taskDescription = addTaskMatch[1].trim();
            } else {
                taskDescription = command; // Fallback
            }
        } else if (result.intent === 'CompleteTask' || result.intent === 'DeleteTask') {
            const actionMatch = command.match(/(?:mark|complete|finish|i completed|task done:|delete|cancel|remove|get rid of)\s*(?:the task of|task named)?\s*(.+)/i);
            if (actionMatch && actionMatch[1]) {
                taskDescription = actionMatch[1].replace(/(?:as done|task|from my list|the)/i, '').trim();
            } else {
                taskDescription = command; // Fallback
            }
        }

        // Format the result to match a structure that your Spring Boot backend's Dialogflow webhook
        // might expect, or a simplified version for your Spring Boot to parse.
        const formattedResultForSpringBoot = {
            queryResult: {
                intent: {
                    displayName: result.intent || 'UnknownIntent'
                },
                parameters: {
                    task: taskDescription
                }
            },
            // A simple fulfillment text if Spring Boot doesn't generate one
            fulfillmentText: `Intent recognized: ${result.intent}.` + (taskDescription ? ` Task: "${taskDescription}".` : '')
        };

        // Now, forward this structured data to your Spring Boot backend
        const springBootBackendUrl = 'http://localhost:8080/api/dialogflow/webhook'; // CONFIRM YOUR SPRING BOOT WEBHOOK URL
        const springBootResponse = await fetch(springBootBackendUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formattedResultForSpringBoot)
        });

        if (!springBootResponse.ok) {
            const errorText = await springBootResponse.text();
            throw new Error(`Spring Boot Backend error: ${springBootResponse.status} - ${errorText}`);
        }

        const finalResponseFromSpringBoot = await springBootResponse.json();
        res.json(finalResponseFromSpringBoot); // Return Spring Boot's response to the frontend

    } catch (nlpError) {
        console.error('Error in Node-NLP processing or forwarding to Spring Boot:', nlpError);
        res.status(500).json({ error: 'Error processing command with NLP model or communicating with main backend.', details: nlpError.message });
    }
});

// Start loading the model, then start the server
loadModel().then(() => {
    app.listen(port, () => {
        console.log(`Node-NLP backend for task management listening at http://localhost:${port}`);
    });
}).catch(err => {
    console.error("Failed to start server due to model loading error:", err);
});