package it.uniba.dib.sms222334.Views.RecycleViews.RequestReport;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.GeoPoint;

import it.uniba.dib.sms222334.Models.Animal;
import it.uniba.dib.sms222334.Models.Report;
import it.uniba.dib.sms222334.Models.User;
import it.uniba.dib.sms222334.R;
import it.uniba.dib.sms222334.Utils.CoordinateUtilities;
import it.uniba.dib.sms222334.Utils.DateUtilities;
import it.uniba.dib.sms222334.Utils.LocationTracker;

public class ReportViewHolder extends RecyclerView.ViewHolder {
    private Context context;

    private Report report;

    private TextView userName;
    private ImageView userPhoto;
    private TextView type;
    private TextView description;
    private TextView distance;
    private ImageView photo;
    private TextView animalName;
    private TextView animalSpeciesAndAge;

    public ReportViewHolder(View itemView, Context context) {
        super(itemView);

        this.context = context;

        userName = itemView.findViewById(R.id.creator_name);
        userPhoto = itemView.findViewById(R.id.profile_picture);
        type = itemView.findViewById(R.id.type);
        description = itemView.findViewById(R.id.description_text);
        distance = itemView.findViewById(R.id.distance_text);
        photo = itemView.findViewById(R.id.image);
        animalName = itemView.findViewById(R.id.name_text);
        animalSpeciesAndAge = itemView.findViewById(R.id.species_age_text);
    }

    public void bind(Report report) {
        this.report = report;

        User user = report.getUser();
        if (user != null) {
            this.userName.setText(user.getName());
            this.userPhoto.setImageBitmap(user.getPhoto());
        } else {
            this.userName.setText(context.getString(R.string.user_not_logged_report));
            this.userPhoto.setImageDrawable(context.getDrawable(R.drawable.default_profile_image));
        }

        this.type.setText(report.getReportTypeString(report.getType(), context));
        this.description.setText(report.getDescription());
        this.photo.setImageBitmap(report.getReportPhoto());

        String animalName = report.getAnimalName();
        if (!animalName.equals(""))
            this.animalName.setText(animalName);
        else
            this.animalName.setText(context.getString(R.string.animal_name_unknown_report));

        this.animalSpeciesAndAge.setText(Animal.getSpeciesString(report.getAnimalSpecies(), context) + (report.getAnimalAge()==null?"":(", "+ DateUtilities.calculateAge(report.getAnimalAge(), context))));
    }

    public void updateDistance() {
        Location devicePosition = LocationTracker.getInstance(context).getLocation();
        if (devicePosition != null) {
            float distance = CoordinateUtilities.calculateDistance(new GeoPoint(devicePosition.getLatitude(), devicePosition.getLongitude()), report.getLocation());
            report.setDistance(distance);
            this.distance.setText(CoordinateUtilities.formatDistance(distance));
        }
    }
}
