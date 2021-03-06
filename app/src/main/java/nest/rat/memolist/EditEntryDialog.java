package nest.rat.memolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by mollyjester on 30.12.2016.
 */

public class EditEntryDialog extends DialogFragment {
    static public final String TAG = "EditEntryDialog";
    private EditText etNewEntryText;
    private long itemId;
    private String itemTxt;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemTxt() {
        return itemTxt;
    }

    public void setItemTxt(String itemTxt) {
        this.itemTxt = itemTxt;
    }

    public interface EditEntryDialogListener {
        void onDialogPositiveClick(long id, String txt);
    }

    private EditEntryDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        mListener = listener(activity);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_entry_dialog, null);
        builder.setView(view);

        etNewEntryText = (EditText) view.findViewById(R.id.etNewEntryText);

        if (itemTxt.length() != 0) {
            etNewEntryText.setText(itemTxt);
        }

        this.addOkButton(builder);
        this.addCancelButton(builder);

        return builder.create();
    }

    private void addOkButton(AlertDialog.Builder builder){
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogPositiveClick(itemId, etNewEntryText.getText().toString());
            }
        });
    }

    private void addCancelButton(AlertDialog.Builder builder){
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditEntryDialog.this.getDialog().cancel();
            }
        });
    }

    private EditEntryDialogListener listener(Activity activity) {
        try {
            return (EditEntryDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement EditEntryDialogListener");
        }
    }
}
