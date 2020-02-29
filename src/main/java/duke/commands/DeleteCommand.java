package duke.commands;

import duke.asset.IllegalDukeException;
import duke.asset.Storage;
import duke.asset.Ui;
import duke.tasks.Task;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import static java.lang.Integer.parseInt;
/**
 * This is a sub class of the Command class in Duke.<br>
 * This class deletes a Task from the ArrayList based on the task<br>
 * number stated by the User.<br>
 */
public class DeleteCommand extends Command  {
    private int index;
    boolean isAll= false;
    public static final String INDEX_OUT_OF_RANGE = "\t Task number provided is not valid. Press \"list\" to see" +
            " available list of task numbers";
    public static final String ALL_TASKS_ALREADY_DELETED ="\t List is already empty!";
    /**
     * This constructor creates a DeleteCommand.<br>
     * @param fullCommand This is the input entered by user that has<br>
     *                    been split into an array of Strings.<br>
     */
    public DeleteCommand(String[] fullCommand){
        super(fullCommand);
        if(fullCommand[1].equals("all")){
            this.isAll=true;
        }else {
            this.index = parseInt(fullCommand[1]) - 1;
        }
    }
    @Override
    public void execute(ArrayList<Task> l1, Ui ui, Storage storage) throws IllegalDukeException, FileNotFoundException {

            if(l1.isEmpty()){
                throw new IllegalDukeException(ALL_TASKS_ALREADY_DELETED);
            }else {
                if (this.isAll) {
                    ui.confirmDeleteAll();
                    ui.printLine();
                    String confirmation = ui.getUserIn().toLowerCase();
                    ui.printLine();
                    while (!confirmation.equals("y") && !confirmation.equals("n")) {
                        ui.printYesOrNoOnly();
                        ui.printLine();
                        confirmation = ui.getUserIn().toLowerCase();
                        ui.printLine();
                    }
                    if (confirmation.equals("y")) {
                        l1.clear();
                        ui.printDeleteAll();
                    } else if (confirmation.equals("n")) {
                        ui.ignoreDeleteAll();
                    }
                } else {
                    if (this.index >= l1.size()) {
                        throw new IllegalDukeException(INDEX_OUT_OF_RANGE);
                    } else {
                        Task task = l1.get(this.index);
                        l1.remove(this.index);
                        ui.printDelete(task, l1);
                    }
                }
            }
        storage.saveFile(l1);
    }

}
