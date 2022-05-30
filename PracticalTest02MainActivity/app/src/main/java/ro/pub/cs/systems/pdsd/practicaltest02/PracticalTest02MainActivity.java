package ro.pub.cs.systems.pdsd.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02MainActivity extends AppCompatActivity {

    private EditText serverPortEditText;
    private EditText clientPortEditText;
    private EditText clientAddressEditText;
    private EditText clientWordEditText;
    private Button serverConnectButton;
    private Button clientSearchButton;
    private TextView resultTextView;

    private ServerThread serverThread = null;


    private ButtonClickListener searchButtonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ClientAsyncTask clientAsyncTask = new ClientAsyncTask(resultTextView);
            clientAsyncTask.execute(clientAddressEditText.getText().toString(), clientPortEditText.getText().toString(), clientWordEditText.getText().toString());
        }
    }

    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));

            serverThread.startServer();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = (EditText) findViewById(R.id.editTextPortServer);

        serverConnectButton = (Button) findViewById(R.id.buttonStartServer);
        serverConnectButton.setOnClickListener(connectButtonClickListener);

        clientPortEditText = (EditText) findViewById(R.id.editTextPortClient);
        clientAddressEditText = (EditText) findViewById(R.id.editTextAddressClient);

        clientSearchButton = (Button) findViewById(R.id.buttonSearchClient);
        clientSearchButton.setOnClickListener(searchButtonClickListener);

        clientWordEditText = (EditText) findViewById(R.id.editTextWordClient);
        resultTextView = (TextView) findViewById(R.id.textViewResult);

    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
        if (serverThread != null) {
            serverThread.stopServer();
        }
        super.onDestroy();
    }
}