package id.niteroomcreation.calculatorcamera.presenter.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.niteroomcreation.calculatorcamera.databinding.IMainBinding
import id.niteroomcreation.calculatorcamera.domain.entity.InOutModel

/**
 * Created by Septian Adi Wijaya on 23/02/2023.
 * please be sure to add credential if you use people's code
 */
class MainAdapter(private var data: List<InOutModel>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    fun submit(update:List<InOutModel>){
        data = update
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            IMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binds(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(private val binding: IMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binds(inOut: InOutModel) {
            binding.iMainInput.text = inOut.inStr
            binding.iMainOutput.text = inOut.outStr
        }
    }
}