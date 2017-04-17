package com.example.daniaskar.fasilbogor;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;

public class MainMenu extends ListActivity {
    String menu;
    int indexPosition;
    private LayoutInflater myInflater;
    private Vector<RowData> data;
    RowData rd;
    AlertDialog.Builder notify;
    String dataAll;
    KategoriParser kategoriParser = new KategoriParser();
    List<Kategori> kategori = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        dataAll = getIntent().getExtras().getString("data");
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getListView().setTextFilterEnabled(true);
        new BackgroundLoad().execute();
    }

    public class BackgroundLoad extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void result) {
            CustomAdapter adapter = new CustomAdapter(MainMenu.this,
                    R.layout.inboxlistitem, R.id.tv_inbox_Title, data);
            MainMenu.this.setListAdapter(adapter);
            getListView().setTextFilterEnabled(true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                kategori = kategoriParser.parse(dataAll);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            data = new Vector<RowData>();
            for (int i = 0; i < kategori.size(); i++) {
                try {
                    rd = new RowData(i, kategori.get(i).getNama_kategory());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                data.add(rd);
            }
            return null;
        }

    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        indexPosition = position;
        new OnItemClickedTask().execute();
    }

    private class RowData {
        protected String inboxLabel;

        RowData(int icon, String sinboxLabel) {
            this.inboxLabel = sinboxLabel;

        }
    }

    private class CustomAdapter extends ArrayAdapter<RowData> {
        public CustomAdapter(Context context, int resource,
                             int textViewResourceId, List<RowData> objects) {
            super(context, resource, textViewResourceId, objects);
            myInflater = LayoutInflater.from(context);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            TextView mainMenuLabel = null;
            TextView inboxDate = null;
            RowData rowData = getItem(position);
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.inboxlistitem, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            mainMenuLabel = holder.getlabel();
            mainMenuLabel.setText(rowData.inboxLabel);
            return convertView;
        }

        private class ViewHolder {
            private View myRow;
            private TextView inboxLabel = null;
            private TextView inboxDate = null;

            public ViewHolder(View myRow) {
                this.myRow = myRow;
            }

            public TextView getlabel() {
                if (null == inboxLabel) {
                    inboxLabel = (TextView) myRow
                            .findViewById(R.id.tv_inbox_Title);
                }
                return inboxLabel;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        // restartSession();
    }

    private class OnItemClickedTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("CLICKED AT", kategori.get(indexPosition).getNama_kategory()+"id : "+kategori.get(indexPosition).getId_kategori());
            menu = kategori.get(indexPosition).getId_kategori();
        }

        @Override
        protected void onPostExecute(Void result) {
            onAction(menu);
        }

    }

    private void lockScreenRotation() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void onAction(String menu) {
        //koneksi ke server / query DB
        Intent intent = null;
        intent = new Intent(MainMenu.this, ListInfoActivity.class);
        intent.putExtra("id_kategori", menu);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}

