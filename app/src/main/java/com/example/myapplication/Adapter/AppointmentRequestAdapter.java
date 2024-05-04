package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.AppointmentRequest;
import com.example.myapplication.R;

import java.util.List;

public class AppointmentRequestAdapter extends RecyclerView.Adapter<AppointmentRequestAdapter.ViewHolder>{
    private List<AppointmentRequest> mAppointmentRequests;
    private Context mContext;
    public AppointmentRequestAdapter(Context mContext, List<AppointmentRequest> mAppointmentRequests) {
        this.mContext = mContext;
        this.mAppointmentRequests = mAppointmentRequests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.appointment_requests_item, parent, false);
        return new AppointmentRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentRequest appointmentRequest = mAppointmentRequests.get(position);
        holder.title.setText(appointmentRequest.getPatientUid());
        // need to add image of the patient logic here
    }

    @Override
    public int getItemCount() {
        return mAppointmentRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
