const { NlpManager } = require('node-nlp');
const manager = new NlpManager({ languages: ['en'], forceNER: true });

// === Add your training data ===
// === Default Welcome Intent ===
manager.addDocument('en', 'just going to say hi', 'Default Welcome Intent');
manager.addDocument('en', 'heya', 'Default Welcome Intent');
manager.addDocument('en', 'hello hi', 'Default Welcome Intent');
manager.addDocument('en', 'howdy', 'Default Welcome Intent');
manager.addDocument('en', 'hey there', 'Default Welcome Intent');
manager.addDocument('en', 'hi there', 'Default Welcome Intent');
manager.addDocument('en', 'greetings', 'Default Welcome Intent');
manager.addDocument('en', 'hey', 'Default Welcome Intent');
manager.addDocument('en', 'long time no see', 'Default Welcome Intent');
manager.addDocument('en', 'hello', 'Default Welcome Intent');
manager.addDocument('en', 'lovely day isn\'t it', 'Default Welcome Intent');
manager.addDocument('en', 'I greet you', 'Default Welcome Intent');
manager.addDocument('en', 'hello again', 'Default Welcome Intent');
manager.addDocument('en', 'hi', 'Default Welcome Intent');
manager.addDocument('en', 'hello there', 'Default Welcome Intent');
manager.addDocument('en', 'a good day', 'Default Welcome Intent');

// === DeleteTask ===
manager.addDocument('en', 'Delete complete coding assignment task', 'DeleteTask');
manager.addDocument('en', 'Cancel send email to client', 'DeleteTask');
manager.addDocument('en', 'Remove book flight tickets from my list', 'DeleteTask');
manager.addDocument('en', 'Get rid of the dentist appointment task', 'DeleteTask');
manager.addDocument('en', 'Delete task named pay electricity bill', 'DeleteTask');
// === AddTask ===
manager.addDocument('en', 'Please remind me to send email to client', 'AddTask');
manager.addDocument('en', 'Add a task called complete coding assignment', 'AddTask');
manager.addDocument('en', 'I want to add prepare presentation slides', 'AddTask');
manager.addDocument('en', 'New task: schedule dentist appointment', 'AddTask');
manager.addDocument('en', 'Remind me to pay electricity bill', 'AddTask');

// === CompleteTask ===
manager.addDocument('en', 'Mark prepare presentation slides as done', 'CompleteTask');
manager.addDocument('en', 'Complete the task of booking tickets', 'CompleteTask');
manager.addDocument('en', 'Finish pay electricity bill task', 'CompleteTask');
manager.addDocument('en', 'I completed clean the room', 'CompleteTask');
manager.addDocument('en', 'Task done: schedule dentist appointment', 'CompleteTask');

// === ListTasks ===
manager.addDocument('en', 'What are my tasks', 'ListTasks');
manager.addDocument('en', 'List everything I need to do', 'ListTasks');
manager.addDocument('en', 'Show my to-do list', 'ListTasks');
manager.addDocument('en', 'What do I have to complete', 'ListTasks');
manager.addDocument('en', 'Tell me my tasks', 'ListTasks');


(async () => {
  await manager.train();
  manager.save('./model.nlp');  // Saves model
  console.log('Model trained and saved as model.nlp');
})();
