package com.test.viewpagedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.study.point.R;
import one.block.eosiojavaabieosserializationprovider.AbiEosSerializationProviderImpl;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        String results = "Waiting for AbiEosSerializationProviderImpl";
        try {
            AbiEosSerializationProviderImpl abiEosSerializationProviderImpl = new AbiEosSerializationProviderImpl();
            String contractStrOrig = "eosio.assert";
            long name64 = abiEosSerializationProviderImpl.stringToName64(contractStrOrig);
            String testStr = abiEosSerializationProviderImpl.name64ToString(name64);

            results = String
                    .format("Original: %s\nname64: %d\ntest: %s", contractStrOrig, name64, testStr);
            tv.setText(results);
        } catch (Exception serializationProviderError) {
            results = "Error from SerializationProvider: " + serializationProviderError.getLocalizedMessage();
        }
        tv.setText(results);
    }

}
