package com.martina.obligatoriov0_1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.martina.obligatoriov0_1.DetalleActivity;
import com.martina.obligatoriov0_1.HubActivity;
import com.martina.obligatoriov0_1.MapaGeneralActivity;
import com.martina.obligatoriov0_1.R;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.metodos.MetodosHub;

import java.io.Serializable;
import java.util.List;



/**
 * Muestra como implementar un Adapter sencillo para un RecyclerView.
 * Muestra como agregar un click listener para cada item en el ViewHolder.
 */
public class HubAdapter extends RecyclerView.Adapter<HubAdapter.itemViewHolder> {

    private final List<Integer> idList;
    private final List<String> estadoList;
    private final List<String> origenList;

    private final LayoutInflater inflater;
    private OnItemSelectedListener onItemSelectedListener;
    public HubAdapter(Context context, List<Integer> idListt, List<String> origenListt,  List<String> estadoListt) {
        this.inflater = LayoutInflater.from(context);
        this.estadoList=estadoListt;
        this.origenList=origenListt;
        this.idList = idListt;
    }
    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = inflater.inflate(R.layout.item_transportation, parent, false);
        return new itemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, int position) {

        holder.bind(position,holder);

    }

    @Override
    public int getItemCount() {
        return idList.size();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int position, int iditem );
    }

    class itemViewHolder extends RecyclerView.ViewHolder {
        final TextView lbl_ID;
        final TextView lbl_Estado;
        final TextView lbl_Origen;


        itemViewHolder(View itemView) {
            super(itemView);
            lbl_ID = itemView.findViewById(R.id.lbl_Rv_Item_id);
            lbl_Estado = itemView.findViewById(R.id.lbl_Rv_Item_estado);
            lbl_Origen = itemView.findViewById(R.id.lbl_Rv_Item_origen);

        }

        void bind(final int position, itemViewHolder holder) {

             final int current_id = idList.get(position);
             String current_estado = estadoList.get(position);
             String current_origen = origenList.get(position);


            holder.lbl_ID.setText(String.valueOf(current_id));

            holder.lbl_Estado.setText(String.valueOf(current_estado));
            holder.lbl_Origen.setText(String.valueOf(current_origen));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelected(position, current_id);
                    }
                    int id = idList.get(position);
                    Intent intent = new Intent(v.getContext(), DetalleActivity.class);
                    intent.putExtra(Constantes.ID_SELECCIONADA,id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);


                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent = new Intent(view.getContext(), MapaGeneralActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constantes.ID_LIST_EXTRA_INTENT, (Serializable)idList);
                    view.getContext().startActivity(intent);

                    return false;
                }
            });

        }

    }

}
