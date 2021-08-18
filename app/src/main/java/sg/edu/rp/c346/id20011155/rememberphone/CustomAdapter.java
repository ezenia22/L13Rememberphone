package sg.edu.rp.c346.id20011155.rememberphone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Phone> phoneList;

    public CustomAdapter(Context context, int resource, ArrayList<Phone> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        phoneList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);
        TextView tvName = rowView.findViewById(R.id.tvName);
        TextView tvNumber = rowView.findViewById(R.id.tvNumber);

        Phone currentVersion = phoneList.get(position);

        tvName.setText(currentVersion.getName());
        tvNumber.setText(currentVersion.getNumbers());

        return rowView;
    }
}
