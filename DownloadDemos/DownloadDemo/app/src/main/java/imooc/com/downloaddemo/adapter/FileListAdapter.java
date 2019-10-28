package imooc.com.downloaddemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import imooc.com.downloaddemo.R;
import imooc.com.downloaddemo.entities.FileInofo;
import imooc.com.downloaddemo.services.DownloadService;

public class FileListAdapter extends BaseAdapter {

    private Context mContext;
    private List<FileInofo> mFileList;

    public FileListAdapter(Context mContext, List<FileInofo> mFileList) {
        this.mContext = mContext;
        this.mFileList = mFileList;
    }

    @Override
    public int getCount() {
        return mFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FileInofo fileInofo = mFileList.get(position);
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.btnStart = convertView.findViewById(R.id.btStart);
            holder.btnStop = convertView.findViewById(R.id.btStop);
            holder.tvFileName = convertView.findViewById(R.id.tvFileName);
            holder.pbProgress = convertView.findViewById(R.id.pbProgress);

            holder.tvFileName.setText(fileInofo.getFileName());
            holder.pbProgress.setMax(100);

            holder.btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DownloadService.class);
                    intent.setAction(DownloadService.ACTION_START);
                    intent.putExtra("fileInfo",fileInofo);
                    mContext.startService(intent);
                }
            });
            holder.btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DownloadService.class);
                    intent.setAction(DownloadService.ACTION_STOP);
                    intent.putExtra("fileInfo",fileInofo);
                    mContext.startService(intent);
                }
            });

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.pbProgress.setProgress((int) fileInofo.getFinished());

        return convertView;
    }

    public void updateProgress(int id, int progress){
        FileInofo fileInofo = mFileList.get(id);
        fileInofo.setFinished(progress);
        notifyDataSetChanged();
    }

    static class ViewHolder{
        TextView tvFileName;
        Button btnStop, btnStart;
        ProgressBar pbProgress;
    }
}
