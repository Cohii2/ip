package duke;

import java.util.List;

import static duke.print.*;

public class TaskList {
    protected List<Task> taskList;

    public TaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    /**
     * Prints list of tasks.
     */
    public void printList(){
        printLine();
        for(int i = 0; i < taskList.size(); i++){
            Task task = taskList.get(i);
            System.out.println((i + 1) + "." + task);
        }
        printLine();
    }

    /**
     * Prints a newly-added task.
     */
    public void printAddedTask(){
        Task lastTask = taskList.get(taskList.size() - 1);
        printMessage("Got it. I've added this task:\n"
                + "  " + lastTask + "\n"
                + "Now you have " + taskList.size() + " tasks in the list.");
    }

    /**
     * Adds a to-do task to list of tasks.
     *
     * @param argument information of to-do to be added.
     */
    public void addToDo(String argument)
            throws MissingParamsException {
        Task newTask = new ToDo(argument);
        taskList.add(newTask);
        printAddedTask();
    }

    /**
     * Adds a deadline task to list of tasks.
     *
     * @param argument information of deadline to be added.
     */
    public void addDeadline(String argument)
            throws MissingParamsException {
        String[] tokens = argument.split("/");
        String description = "", by = "";

        for(String token : tokens){
            String[] subTokens = token.split(" ", 2);
            String subCommand = subTokens[0].toLowerCase().trim();
            String subArgument = "";
            if (subTokens.length > 1) {
                subArgument = subTokens[1].trim();
            }

            if (subCommand.equalsIgnoreCase("by")) {
                by = subArgument;
            } else {
                description = token.trim();
            }
        }

        Task newTask = new Deadline(description, by);
        taskList.add(newTask);
        printAddedTask();
    }

    /**
     * Adds an event task to list of tasks.
     *
     * @param argument information of deadline to be added.
     */
    public void addEvent(String argument)
            throws MissingParamsException {
        String[] tokens = argument.split("/");
        String description = "", start = "", end = "";

        for(String token : tokens){
            String[] subTokens = token.split(" ", 2);
            String subCommand = subTokens[0].toLowerCase().trim();
            String subArgument = "";
            if (subTokens.length > 1) {
                subArgument = subTokens[1].trim();
            }

            switch (subCommand){
            case "from":
                start = subArgument;
                break;
            case "to":
                end = subArgument;
                break;
            default:
                description = token.trim();
                break;
            }
        }

        Task newTask = new Event(description, start, end);
        taskList.add(newTask);
        printAddedTask();
    }

    /**
     * Marks task as done or undone.
     *
     * @param instruction User instruction on which task to mark.
     * @param done Status of task - done or undone.
     */
    public void markTask(String instruction, boolean done)
            throws DukeException.InvalidIntegerException,
            DukeException.IntegerOutOfBoundsException {
        int taskNumber;

        try {
            taskNumber = Integer.parseInt(instruction);
        } catch (NumberFormatException e) {
            throw new DukeException.InvalidIntegerException();
        }

        try {
            taskList.get(taskNumber - 1).setDone(done);
            printMessage("Nice! I've marked this task as " + (done ? "done\n" : "undone\n")
                    + "  " + taskList.get(taskNumber - 1));
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException.IntegerOutOfBoundsException();
        }
    }

    /**
     * Deletes task specified by User.
     *
     * @param instruction User instruction on which task to delete.
     */
    public void deleteTask(String instruction)
            throws DukeException.InvalidIntegerException,
            DukeException.IntegerOutOfBoundsException {
        int taskNumber;

        try {
            taskNumber = Integer.parseInt(instruction);
        } catch (NumberFormatException e) {
            throw new DukeException.InvalidIntegerException();
        }

        try {
            Task taskToDelete = taskList.get(taskNumber - 1);
            taskList.remove(taskNumber - 1);
            printMessage(" Noted. I've removed this task:\n"
                    + "  " + taskToDelete);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException.IntegerOutOfBoundsException();
        }
    }
}
