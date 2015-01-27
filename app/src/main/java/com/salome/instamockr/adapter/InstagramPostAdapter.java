package com.salome.instamockr.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.salome.instamockr.R;
import com.salome.instamockr.utility.RoundedTransformation;
import com.salome.instamockr.model.InstagramPost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by saianudeepm on 1/26/15.
 */
public class InstagramPostAdapter extends ArrayAdapter<InstagramPost> {

    public InstagramPostAdapter(Context context, ArrayList<InstagramPost> posts){

        super(context, R.layout.item_instagram_post, posts);
        
    }
        @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * Get the item
         * Create the xml template
         * Stick the data into the xml template
         * Return the view 
        **/
        // Get the data item for this position
        InstagramPost post = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        
        if (convertView == null) {
            viewHolder = new ViewHolder();
            
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_instagram_post, parent, false);
            
            viewHolder.userProfileImageView = (ImageView) convertView.findViewById(R.id.ivUserProfile);
            viewHolder.userNameTextView = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.posterImageView = (ImageView) convertView.findViewById(R.id.ivPoster);
            viewHolder.captionTextView = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.likesTextView = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.relativeTimeTextView = (TextView) convertView.findViewById(R.id.tvRelativeTime);
            
            convertView.setTag(viewHolder);
            
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        // Populate the data into the template view using the data object

        viewHolder.userNameTextView.setText(post.getUserName());
        viewHolder.captionTextView.setText(post.getCaption());
        viewHolder.likesTextView.setText(String.valueOf(post.getLikes())+" likes");
        //String timerel = (String) DateUtils.getRelativeTimeSpanString(post.getCreatedTime()*1000);
        viewHolder.relativeTimeTextView.setText((String) DateUtils.getRelativeTimeSpanString(post.getCreatedTime()*1000) );
        Picasso.with(this.getContext())
                .load(post.getUserProfileImageUrl())
                .transform(new RoundedTransformation(100, 0))
                .fit()
                .placeholder(R.drawable.ic_user)
                .into(viewHolder.userProfileImageView);
        Picasso.with(this.getContext())
                .load(post.getImageUrl())
                .placeholder(R.drawable.ic_placeholder_image)
                .into(viewHolder.posterImageView);

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView userNameTextView;
        TextView captionTextView;
        ImageView posterImageView;
        ImageView userProfileImageView;
        TextView likesTextView;
        TextView relativeTimeTextView;
    }

}
