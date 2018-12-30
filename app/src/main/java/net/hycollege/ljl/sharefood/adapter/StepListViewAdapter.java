package net.hycollege.ljl.sharefood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.hycollege.ljl.sharefood.R;
import net.hycollege.ljl.sharefood.bean.FoodDetailBean;
import net.hycollege.ljl.sharefood.utils.StringUtils;

import java.util.List;


public class StepListViewAdapter extends BaseAdapter{
    Context mContext;
    List<FoodDetailBean.ResultBean.ListBean.ProcessBean> mList;

    public StepListViewAdapter(Context context, List<FoodDetailBean.ResultBean.ListBean.ProcessBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fooddetail_step, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //图片地址
        final String imageUrl = mList.get(position).getPic();
        //Glide设置图片(未加载则用粉色替代)
        /*Glide.with(mContext)
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .placeholder(R.color.colorAccent)
                .into(holder.iv_step);*/
        Glide.with(mContext).load(imageUrl).into(holder.iv_step);
        //设置文字描述的步骤
        holder.tv_step.setText(StringUtils.removeOtherStr((position + 1) + "."+ mList.get(position).getPcontent()));
        return convertView;
    }

    class ViewHolder {
        //@BindView()
        ImageView iv_step;
        //@BindView(R.id.tv_step)
        TextView tv_step;

        public ViewHolder(View convertView) {
            //ButterKnife.bind(this, convertView);
            iv_step=convertView.findViewById(R.id.iv_step);
            tv_step=convertView.findViewById(R.id.tv_step);
        }
    }
}
