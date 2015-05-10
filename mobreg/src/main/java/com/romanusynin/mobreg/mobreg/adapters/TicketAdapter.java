package com.romanusynin.mobreg.mobreg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.Ticket;

import java.util.ArrayList;

public class TicketAdapter extends ArrayAdapter<Ticket> {

    public TicketAdapter(Context context, ArrayList<Ticket> tickets) {
        super(context, 0, tickets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ticket ticket = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ticket_item_lv, parent, false);
        }
        TextView tvTime = (TextView) convertView.findViewById(R.id.ticketTime);
        tvTime.setText(ticket.getTime());
        return convertView;
    }

}
