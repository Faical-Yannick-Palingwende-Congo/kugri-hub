/*=============================================================================================*/
/* Class            : MessageAdapter                                                           */
/*                                                                                             */
/* Description      : MessageAdapter class for customizing list view adapter.                  */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.helloworld.adapter;

import java.util.LinkedList;

import com.kugri.frontend.client.android.helloworld.R;
import com.kugri.frontend.client.android.helloworld.entity.Message;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * MessageAdapter class for customizing list view adapter.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class MessageAdapter extends BaseAdapter {
 
	/** The adapater activity*/
    private Activity activity;
    /** The adapter data list */
    private LinkedList<Message> data;
    /** The adapter layout inflater */
    private static LayoutInflater inflater=null;
 
    /**
	 * Called to build the adapater instance
	 * 
	 * @param a the adapter activity instance
	 * @param d the adapter data list instance
	 */
    public MessageAdapter(Activity a, LinkedList<Message> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    /**
	 * Called to get the size of the data list
	 * 
	 * @return int that is the data size
	 */
    public int getCount() {
        return data.size();
    }
 
    /**
	 * Called to get an item
	 * 
	 * @param position the index of the item
	 * @return Object that contains the item instance
	 */
    public Object getItem(int position) {
        return data.get(position);
    }
 
    /**
	 * Called to get an item id
	 * 
	 * @param position the index of the item
	 * @return long that is the item identifier
	 */
    public long getItemId(int position) {
        return position;
    }
 
    /**
	 * Called to get the rendered view
	 * 
	 * @param position the position of the view
	 * @param convertView the view converter instance
	 * @param parent the view parent
	 * @return View that contains the view instance
	 */
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.listrow, null);
        
        TextView sender = (TextView)vi.findViewById(R.id.message_sender);
        TextView receiver = (TextView)vi.findViewById(R.id.message_receiver);
        TextView content = (TextView)vi.findViewById(R.id.message_content);
//        TextView stamp = (TextView)vi.findViewById(R.id.notification_stamp);
//        TextView status = (TextView)vi.findViewById(R.id.notification_stamp);
 
        Message message = new Message();
        message = data.get(position);
 
        sender.setText(message.getSender());
        receiver.setText(message.getReceiver());
        content.setText(message.getContent());
//        stamp.setText(message.getStamp());
//        status.setText(message.getStatus());
        return vi;
    }
}
