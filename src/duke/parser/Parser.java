package duke.parser;

import duke.command.*;
import duke.exception.*;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    private static final int TASK_TYPE = 0;
    private static final int COMMAND_DESCRIPTION = 1;

    public static Command parse(String fullCommand) throws DukeException {
        String[] arr = fullCommand.trim().split(" ", 2);
        String taskType = arr[TASK_TYPE];
        switch (taskType.toUpperCase()) {
            case "BYE":
                return new ExitCommand();

            case "LIST":
                return new ListCommand();

            case "MARK":
                int indexOfTaskToMark = parseToTaskIndex(arr);
                return new MarkCommand(true, indexOfTaskToMark);

            case "UNMARK":
                int indexOfTaskToUnmark = parseToTaskIndex(arr);
                return new MarkCommand(false, indexOfTaskToUnmark);

            case "DELETE":
                int indexOfTaskToDelete = parseToTaskIndex(arr);
                return new DeleteCommand(indexOfTaskToDelete);

            case "TODO":
                return parseToAddTodoCommand(arr);

            case "DEADLINE":
                return parseToAddDeadlineCommand(arr);

            case "EVENT":
                return parseToAddEventCommand(arr);

            case "HELP":
                return new HelpCommand();

            default:
                throw new InvalidCommandException();
        }

    }

    private static int parseToTaskIndex(String[] fullCommandArray) throws EmptyIndexException {
        if (fullCommandArray.length == 1) {
            throw new EmptyIndexException();
        }
        return Integer.parseInt(fullCommandArray[COMMAND_DESCRIPTION]);
    }
    private static Command parseToAddTodoCommand(String[] fullCommandArray) throws EmptyDescriptionException {
        if (fullCommandArray.length == 1) {
            throw new EmptyDescriptionException();
        }
        return new AddCommand(new Todo(fullCommandArray[COMMAND_DESCRIPTION]));
    }

    private static Command parseToAddDeadlineCommand(String[] fullCommandArray) throws IllegalInputException {

        simpleDescriptionChecking(fullCommandArray);

        String descriptionAndDate = fullCommandArray[COMMAND_DESCRIPTION];
        String[] descriptionAndDateArray = descriptionAndDate.split("/");

        if (descriptionAndDateArray.length == 1) {
            throw new EmptyDateException();
        }

        String description = descriptionAndDateArray[0];
        LocalDate date = parseToLocalDateTime(descriptionAndDateArray[1]);
        return new AddCommand(new Deadline(description, date));
    }

    private static Command parseToAddEventCommand(String[] fullCommandArray) throws IllegalInputException {

        simpleDescriptionChecking(fullCommandArray);

        String descriptionAndDate = fullCommandArray[COMMAND_DESCRIPTION];
        String[] descriptionAndDateArray = descriptionAndDate.split("/");

        if (descriptionAndDateArray.length == 1) {
            throw new EmptyDateException();
        }

        String description = descriptionAndDateArray[0];
        LocalDate date = parseToLocalDateTime(descriptionAndDateArray[1]);
        return new AddCommand(new Event(description, date));
    }

    public static LocalDate parseToLocalDateTime(String date) throws IllegalDateFormatException {
        LocalDate res = null;
        try {
            res = LocalDate.parse(date.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalDateFormatException();
        }
        return res;
    }

    private static void simpleDescriptionChecking(String[] fullCommandArray) throws EmptyDescriptionException {
        if (fullCommandArray.length == 1) {
            throw new EmptyDescriptionException();
        }
    }
}
