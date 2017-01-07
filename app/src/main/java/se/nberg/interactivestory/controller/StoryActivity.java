package se.nberg.interactivestory.controller;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import se.nberg.interactivestory.R;
import se.nberg.interactivestory.model.Page;
import se.nberg.interactivestory.model.Story;

public class StoryActivity extends AppCompatActivity {

    private Story mStory = new Story();
    private String mName;
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;

    public static final String TAG = StoryActivity.class.getSimpleName();
    private Page mCurrentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));
        if (mName == null) {
            mName = "Friend";
        }
        Log.d(TAG, mName);
        mImageView = (ImageView) findViewById(R.id.storyImageView);
        mTextView = (TextView) findViewById(R.id.storyTextView);
        mChoice1 = (Button) findViewById(R.id.choiceButton1);
        mChoice2 = (Button) findViewById(R.id.choiceButton2);
        loadPage(0);
    }

    private void loadPage(int pageNumber) {
        mCurrentPage = mStory.getPage(pageNumber);
        Drawable drawable = getResources().getDrawable(mCurrentPage.getImageId());
        mImageView.setImageDrawable(drawable);

        String pageText = mCurrentPage.getText();
        //Add name if placeholder included
        pageText = String.format(pageText, mName);
        mTextView.setText(pageText);

        if (!mCurrentPage.isFinal()) {
            mChoice1.setText(mCurrentPage.getChoice1().getText());
            mChoice2.setText(mCurrentPage.getChoice2().getText());
            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        } else {
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice1.setText("Play again");
            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }


    }
}
