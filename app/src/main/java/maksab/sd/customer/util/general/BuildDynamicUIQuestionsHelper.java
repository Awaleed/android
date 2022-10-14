package maksab.sd.customer.util.general;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.utility.QuestionControlTypes;
import maksab.sd.customer.models.orders.details.SpecialtyQuestionAnswerViewModel;
import maksab.sd.customer.models.orders.details.SpecialtyQuestionViewModel;
import maksab.sd.customer.ui.media.viewer.MediaActivityOpener;

public class BuildDynamicUIQuestionsHelper {
    private Context _context;
    private View _answers_layout;
    private LinearLayout _dynamic_view;

    public BuildDynamicUIQuestionsHelper(Context context, View answers_layout,
                                         LinearLayout dynamic_view) {
        _context = context;
        _answers_layout = answers_layout;
        _dynamic_view = dynamic_view;
    }

    public void showAnswers(List<SpecialtyQuestionAnswerViewModel> answers) {
        if (answers == null || answers.size() == 0) {
            _answers_layout.setVisibility(View.GONE);
        }
        else {
            _answers_layout.setVisibility(View.VISIBLE);

            for (SpecialtyQuestionAnswerViewModel answer : answers) {
                SpecialtyQuestionViewModel questionItem = answer.getSpecialtyQuestion();
                if (questionItem.getSpecialtyQuestionControlType() == QuestionControlTypes.FreeText.ordinal()) {
                    LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = inflater.inflate(R.layout.item_text_question_answer, null);

                    TextView question_text_view = (TextView) view.findViewById(R.id.question_text_view);
                    TextView answer_text_view = (TextView) view.findViewById(R.id.answer_text_view);
                    question_text_view.setText(questionItem.getArabicQuestion());
                    answer_text_view.setText(answer.getArabicAnswer());

                    _dynamic_view.addView(view);
                }
                else if (questionItem.getSpecialtyQuestionControlType() == QuestionControlTypes.FreeTextLong.ordinal()) {
                    LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = inflater.inflate(R.layout.item_text_question_answer, null);

                    TextView question_text_view = (TextView) view.findViewById(R.id.question_text_view);
                    TextView answer_text_view = (TextView) view.findViewById(R.id.answer_text_view);
                    question_text_view.setText(questionItem.getArabicQuestion());
                    answer_text_view.setText(answer.getArabicAnswer());

                    _dynamic_view.addView(view);
                }
                else if (questionItem.getSpecialtyQuestionControlType() == QuestionControlTypes.STARS.ordinal()) {
                    LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = inflater.inflate(R.layout.item_star_question_answer, null);

                    TextView question_text_view = (TextView) view.findViewById(R.id.question_text_view);
                    RatingBar rate_answer_view = (RatingBar) view.findViewById(R.id.rate_answer_view);
                    question_text_view.setText(questionItem.getArabicQuestion());
                    rate_answer_view.setRating(Float.parseFloat(answer.getArabicAnswer()));

                    _dynamic_view.addView(view);
                }
                else if (questionItem.getSpecialtyQuestionControlType() == QuestionControlTypes.SingleChoice.ordinal()) {
                    LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = inflater.inflate(R.layout.item_text_question_answer, null);

                    TextView question_text_view = (TextView) view.findViewById(R.id.question_text_view);
                    TextView answer_text_view = (TextView) view.findViewById(R.id.answer_text_view);
                    question_text_view.setText(questionItem.getArabicQuestion());
                    answer_text_view.setText(answer.getArabicAnswer());

                    _dynamic_view.addView(view);
                }
                else if (questionItem.getSpecialtyQuestionControlType() == QuestionControlTypes.MultipleChoice.ordinal()) {
                    LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = inflater.inflate(R.layout.item_text_question_answer, null);

                    TextView question_text_view = (TextView) view.findViewById(R.id.question_text_view);
                    TextView answer_text_view = (TextView) view.findViewById(R.id.answer_text_view);
                    question_text_view.setText(questionItem.getArabicQuestion());
                    answer_text_view.setText(answer.getArabicAnswer());

                    _dynamic_view.addView(view);
                }
                else if (questionItem.getSpecialtyQuestionControlType() == QuestionControlTypes.Image.ordinal()) {
                    LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.item_image_question_answer, null);

                    TextView question_text_view = (TextView) view.findViewById(R.id.question_text_view);
                    ImageView question_image_view = (ImageView) view.findViewById(R.id.question_image_view);
                    question_text_view.setText(questionItem.getArabicQuestion());

                    Picasso.with(_context)
                            .load(answer.getArabicAnswer())
                            .placeholder(R.drawable.placeholder)
                            .into(question_image_view);

                    question_image_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MediaActivityOpener.openViewActivity((BaseActivity)_context, questionItem.getArabicAnswer());
                        }
                    });

                    _dynamic_view.addView(view);
                }
                else if (questionItem.getSpecialtyQuestionControlType() == QuestionControlTypes.GPS.ordinal()) {
                    LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = inflater.inflate(R.layout.field_gps_choice, null);

                    TextView text_view = (TextView) view.findViewById(R.id.text_view);
                    TextView gps_location_text_view = (TextView) view.findViewById(R.id.gps_location_text_view);
                    ImageView image_view = (ImageView) view.findViewById(R.id.image_view);

                    text_view.setText(questionItem.getArabicQuestion());
                    gps_location_text_view.setText(answer.getArabicAnswer());

                    String link = String.format("https://maps.googleapis.com/maps/api/staticmap?markers=color:red|%s&size=600x600&zoom=15&key=AIzaSyCi-TxJLcmswJylE6ULJ3cIT1IHtWHwV-c&language=ar",
                            answer.getArabicAnswer());

                    Picasso.with(_context).load(link).placeholder(R.drawable.placeholder).into(image_view);

                    image_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                    Uri.parse("http://maps.google.com/maps?daddr=" +
                                            questionItem.getArabicAnswer()));
                            _context.startActivity(intent);
                        }
                    });

                    _dynamic_view.addView(view);
                }
            }
        }
    }
}

