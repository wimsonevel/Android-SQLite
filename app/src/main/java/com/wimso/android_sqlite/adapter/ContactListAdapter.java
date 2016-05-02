package com.wimso.android_sqlite.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wimso.android_sqlite.R;
import com.wimso.android_sqlite.listener.RecyclerItemClickListener;
import com.wimso.android_sqlite.model.Contact;
import com.wimso.android_sqlite.widget.LetterTile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wim on 5/1/16.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactHolder>{

    private List<Contact> contactList;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public ContactListAdapter(Context context) {
        this.context = context;
        this.contactList = new ArrayList<>();
    }

    private void add(Contact item) {
        contactList.add(item);
        notifyItemInserted(contactList.size() - 1);
    }

    public void addAll(List<Contact> contactList) {
        for (Contact contact : contactList) {
            add(contact);
        }
    }

    public void remove(Contact item) {
        int position = contactList.indexOf(item);
        if (position > -1) {
            contactList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Contact getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact_item, parent, false);

        final ContactHolder contactHolder = new ContactHolder(view);

        contactHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = contactHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, contactHolder.itemView);
                    }
                }
            }
        });

        return contactHolder;
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        final Contact contact = contactList.get(position);

        final Resources res = context.getResources();
        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);

        LetterTile letterTile = new LetterTile(context);

        Bitmap letterBitmap = letterTile.getLetterTile(contact.getName(),
                String.valueOf(contact.getId()), tileSize, tileSize);

        holder.thumb.setImageBitmap(letterBitmap);
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhone());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    static class ContactHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView name;
        TextView phone;

        public ContactHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);

        }
    }
}
