package com.BinaryBros.Voice_Controlled.Task.Manager.Controllor;

import com.BinaryBros.Voice_Controlled.Task.Manager.Model.Task;
import com.BinaryBros.Voice_Controlled.Task.Manager.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/dialogflow")
public class DialogflowWebhookController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/webhook")
    public Map<String, Object> handleWebhook(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        String reply = "Sorry, I didn't understand that.";

        try {
            Map<String, Object> queryResult = (Map<String, Object>) payload.get("queryResult");
            Map<String, Object> intent = (Map<String, Object>) queryResult.get("intent");
            String intentName = (String) intent.get("displayName");
            Map<String, Object> parameters = (Map<String, Object>) queryResult.get("parameters");

            // Extract task (handle both String and ArrayList cases)
            String taskDesc;
            Object taskObj = parameters.get("task");
            if (taskObj instanceof ArrayList) {
                // Join list items into a single string
                taskDesc = String.join(" ", (ArrayList<String>) taskObj);
            } else {
                taskDesc = (String) taskObj;
            }

            switch(intentName) {
                case "AddTask":
                    taskService.addTask(taskDesc);
                    reply = "Task '" + taskDesc + "' added successfully!";
                    break;

                case "ListTasks":
                    List<Task> tasks = taskService.getAllTasks();
                    if (tasks.isEmpty()) {
                        reply = "You have no tasks.";
                    } else {
                        StringBuilder sb = new StringBuilder("Your tasks are: ");
                        tasks.forEach(task -> sb.append(task.getDescription())
                                .append(" (").append(task.getStatus()).append("), "));
                        reply = sb.substring(0, sb.length() - 2); // Remove last comma
                    }
                    break;

                case "CompleteTask":
                    Optional<Task> taskToComplete = taskService.getAllTasks().stream()
                            .filter(t -> t.getDescription().equalsIgnoreCase(taskDesc))
                            .findFirst();

                    if (taskToComplete.isPresent()) {
                        taskService.markDone(taskToComplete.get().getId());
                        reply = "Task '" + taskDesc + "' marked as completed!";
                    } else {
                        reply = "Task not found: " + taskDesc;
                    }
                    break;

                case "DeleteTask":
                    Optional<Task> taskToDelete = taskService.getAllTasks().stream()
                            .filter(t -> t.getDescription().equalsIgnoreCase(taskDesc))
                            .findFirst();

                    if (taskToDelete.isPresent()) {
                        taskService.deleteTask(taskToDelete.get().getId());
                        reply = "Task '" + taskDesc + "' deleted successfully!";
                    } else {
                        reply = "Task not found: " + taskDesc;
                    }
                    break;
            }
        } catch (Exception e) {
            reply = "An error occurred while processing your request.";
            e.printStackTrace();
        }

        response.put("fulfillmentText", reply);
        return response;
    }

//@PostMapping("/webhook")
//public Map<String, Object> handleWebhook(@RequestBody Map<String, Object> payload) {
//    Map<String, Object> response = new HashMap<>();
//
//    try {
//        Map<String, Object> queryResult = (Map<String, Object>) payload.get("queryResult");
//        String intent = ((Map<String, String>) queryResult.get("intent")).get("displayName");
//        Map<String, Object> parameters = (Map<String, Object>) queryResult.get("parameters");
//        String task = (String) parameters.get("task");
//
//        // Handle entities if present
//        List<Map<String, String>> entities = (List<Map<String, String>>) parameters.get("entities");
//        if (entities != null) {
//            // Process entities (e.g., extract due dates, priorities)
//        }
//
//        String reply = switch (intent) {
//            case "AddTask" -> {
//                taskService.addTask(task);
//                yield "Added: " + task;
//            }
////            case "CompleteTask" -> {
////                taskService.markDone(task);
////                yield "Completed: " + task;
////            }
//            // ... other intents
//            default -> "Processed: " + task;
//        };
//
//        response.put("fulfillmentText", reply);
//
//    } catch (Exception e) {
//        response.put("fulfillmentText", "Error processing request");
//    }
//
//    return response;
//}
}
