package com.victoria.timothykasaga.gorret;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Leontymo on 4/21/2016.
 */
public class ServerRequests {
    public static final int CONNECTION_TIMEOUT = 15000;
    public static final int READ_TIMEOUT = 20000;
    public static final String SERVER2 = "http://10.0.2.2:80/smsd/";
    ProgressDialog progressDialog;
    Context context;
    public ServerRequests(Context paramContext)
    {   context = paramContext;
        progressDialog = new ProgressDialog(paramContext);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait....");
    }

    public void storeAdmin(Admin admin, Create_account create_account){
        progressDialog.show();
        new StoreAdminsAsyncTask(admin, create_account).execute();
    }

    public void authenticateAdmin(Admin admin, Login login){
        progressDialog.show();
        new FetchDataAsyncTask(admin,login).execute();
    }

    private class StoreAdminsAsyncTask extends AsyncTask<Void,Void,String>{
        Admin admin;
        Create_account create_account;

        private StoreAdminsAsyncTask(Admin admin, Create_account create_account) {
            this.admin = admin;
            this.create_account = create_account;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                StringBuilder content = new StringBuilder();
                // URL url = new URL(SERVER+"registerUser.php");
                URL url = new URL("http://10.0.3.2/smsd/registerUser.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(CONNECTION_TIMEOUT);
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("Name",admin.name)
                        .appendQueryParameter("Email", admin.email)
                        .appendQueryParameter("UName", admin.username)
                        .appendQueryParameter("Pass", admin.pass)
                        .appendQueryParameter("Tel", admin.tel);


                String query = builder.build().getEncodedQuery();

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                // read from the urlconnection via the bufferedreader
                while ((line = bufferedReader.readLine()) != null)
                {
                    content.append(line + "\n");
                }
                bufferedReader.close();
                result = content.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            create_account.continueExecution(s,create_account);
        }
    }

    private class FetchDataAsyncTask extends AsyncTask<Void,Void,String>{
        Admin admin;
        Login login;

        StringBuilder content1 = new StringBuilder();
        public FetchDataAsyncTask(Admin admin, Login login) {
            this.admin = admin;
            this.login = login;
        }
        @Override
        protected String doInBackground(Void... params) {
            String res = "";
            try {
                URL url = new URL("http://10.0.3.2/smsd/retrive.php");
                URLConnection urlConnection = url.openConnection();

                urlConnection.setReadTimeout(CONNECTION_TIMEOUT);
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("UName", admin.username)
                        .appendQueryParameter("Pass", admin.pass);
                String query = builder.build().getEncodedQuery();

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                // read from the urlconnection via the bufferedreader
                while ((line = bufferedReader.readLine()) != null)
                {
                    content1.append(line + "\n");
                }
                bufferedReader.close();
                res = content1.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return res;
        }

        @Override
        protected void onPostExecute(String res) {
            progressDialog.dismiss();
            super.onPostExecute(res);
            if(!res.equals("")){
            login.continueExecution(res,login);
            }

        }
    }

}
