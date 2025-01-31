package duke.command;

import java.util.List;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * A FindCommand class that encapsulates the action of find in Duke.
 */
public class FindCommand extends Command {

    public static final String COMMAND = "FIND";

    public static final String MESSAGE_USAGE = COMMAND
            + "\nFinds a task, find <keyword>"
            + "\nExample: find wash";
    private final String stringToFind;

    /**
     * Constructs a FindCommand class.
     * @param stringToFind the string that needs to be found
     */
    public FindCommand(String stringToFind) {
        this.stringToFind = stringToFind;
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        List<Task> taskList = tasks.find(stringToFind).getTasks();
        ui.showFindTask(taskList);
    }
}
