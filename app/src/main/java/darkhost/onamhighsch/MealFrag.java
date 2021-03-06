package darkhost.onamhighsch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 민재 on 2016-06-04.
 */
public class MealFrag extends Fragment {
    public static Context mContext;
    String Code;

    public MealFrag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mealfrag, container, false);
        // Arguments에서 code를 가져와 어떤 급식을 원하는지 확인
        Code = getArguments().getString("code");
        MealLoad(view);
        return view;
    }

    public void MealLoad(View view) {
        // 급식 에러 스트링
        String NoMeal = getResources().getString(R.string.nomeal);

        // 날짜 계산
        String[] monthday = DateCalculator();

        // 이번 달 급식 메뉴 SP 로드
        SharedPreferences data = MealFrag.this.getActivity().getSharedPreferences("meal", Context.MODE_MULTI_PROCESS);
        String mealdb = data.getString("meal", "");

        // 다음 달 급식 메뉴 SP 로드
        SharedPreferences data1 = MealFrag.this.getActivity().getSharedPreferences("mealnm", Context.MODE_MULTI_PROCESS);
        String mealdb1 = data1.getString("mealnm", "");

        // 이번 달 (데이터를 다운한 달) SP 로드
        SharedPreferences data2 = MealFrag.this.getActivity().getSharedPreferences("month", Context.MODE_MULTI_PROCESS);
        int months = data2.getInt("month", 0);

        // 이전 달 급식 메뉴 SP 로드
        SharedPreferences data3 = MealFrag.this.getActivity().getSharedPreferences("mealbm", Context.MODE_MULTI_PROCESS);
        String mealdb2 = data3.getString("mealbm", "");

        // 텍스트뷰 찾기
        TextView monday = (TextView) view.findViewById(R.id.monday);
        TextView tuesday = (TextView) view.findViewById(R.id.tuesday);
        TextView wednesday = (TextView) view.findViewById(R.id.wednesday);
        TextView thursday = (TextView) view.findViewById(R.id.thursday);
        TextView friday = (TextView) view.findViewById(R.id.friday);

        // 메뉴 SP가 비어있지 않은경우
        if (!mealdb.equals("")) {

            MenuData[] meals = new MenuData[31];
            MenuData[] nmmeals = new MenuData[31];
            MenuData[] bmmeals = new MenuData[31];
            try {
                // 현재 달의 급식 데이터 파싱
                Document meal = Jsoup.parse(mealdb);
                meals = MenuDataParser.parse(meal);

                // 다음 달의 급식 데이터 파싱
                Document mealnm = Jsoup.parse(mealdb1);
                nmmeals = MenuDataParser.parse(mealnm);

                // 이전 달의 급식 데이터 파싱
                Document mealbm = Jsoup.parse(mealdb2);
                bmmeals = MenuDataParser.parse(mealbm);

            } catch (Exception e) {
                Log.d("MealFrag", "Parsing Error!");
            }

            // 점심인경우
            if (Code.equals("lunch")) {
                // 월요일
                try {
                    // 날짜 계산에서 가져온 MMdd 형식의 날짜 스트링을 자름
                    int Mondaym = Integer.parseInt(monthday[1].substring(0, 2));
                    int Mondayd = Integer.parseInt(monthday[1].substring(2, 4));

                    // 저장된 달과 월요일의 달이 같을경우
                    if (Mondaym == months) {
                        // 이번달 급식 파싱
                        monday.setText(meals[Mondayd - 1].lunch);
                    } else if (Mondaym > months) {
                        // 다음달 급식 파싱
                        monday.setText(nmmeals[Mondayd - 1].lunch);
                    } else {
                        // 이전달 급식 파싱
                        monday.setText(bmmeals[Mondayd - 1].lunch);
                    }

                } catch (Exception e) {
                    // 에러 텍스트 설정
                    monday.setText(NoMeal);
                }

                // 화요일
                try {
                    int Tuesdaym = Integer.parseInt(monthday[2].substring(0, 2));
                    int Tuesdayd = Integer.parseInt(monthday[2].substring(2, 4));

                    if (Tuesdaym == months) {
                        tuesday.setText(meals[Tuesdayd - 1].lunch);
                    } else if (Tuesdaym > months) {
                        // 다음달 급식 파싱
                        tuesday.setText(nmmeals[Tuesdayd - 1].lunch);
                    } else {
                        // 이전달 급식 파싱
                        tuesday.setText(bmmeals[Tuesdayd - 1].lunch);
                    }


                } catch (Exception e) {
                    tuesday.setText(NoMeal);
                }

                // 수요일
                try {
                    int Wednesdaym = Integer.parseInt(monthday[3].substring(0, 2));
                    int Wednesdayd = Integer.parseInt(monthday[3].substring(2, 4));

                    if (Wednesdaym == months) {
                        wednesday.setText(meals[Wednesdayd - 1].lunch);
                    } else if (Wednesdaym > months) {
                        // 다음달 급식 파싱
                        wednesday.setText(nmmeals[Wednesdayd - 1].lunch);
                    } else {
                        // 이전달 급식 파싱
                        wednesday.setText(bmmeals[Wednesdayd - 1].lunch);
                    }

                } catch (Exception e) {
                    wednesday.setText(NoMeal);
                }

                try {
                    int Thursdaym = Integer.parseInt(monthday[4].substring(0, 2));
                    int Thursdayd = Integer.parseInt(monthday[4].substring(2, 4));

                    if (Thursdaym == months) {
                        thursday.setText(meals[Thursdayd - 1].lunch);
                    } else if (Thursdaym > months) {
                        // 다음달 급식 파싱
                        thursday.setText(nmmeals[Thursdayd - 1].lunch);
                    } else {
                        // 이전달 급식 파싱
                        thursday.setText(bmmeals[Thursdayd - 1].lunch);
                    }

                } catch (Exception e) {
                    thursday.setText(NoMeal);
                }

                try {
                    int Fridaym = Integer.parseInt(monthday[5].substring(0, 2));
                    int Fridayd = Integer.parseInt(monthday[5].substring(2, 4));

                    if (Fridaym == months) {
                        friday.setText(meals[Fridayd - 1].lunch);
                    } else if (Fridaym > months) {
                        // 다음달 급식 파싱
                        friday.setText(nmmeals[Fridayd - 1].lunch);
                    } else {
                        // 이전달 급식 파싱
                        friday.setText(bmmeals[Fridayd - 1].lunch);
                    }

                } catch (Exception e) {
                    friday.setText(NoMeal);
                }
            }
            // 저녁일경우
            else if(Code.equals("dinner"))
            {
                // 월요일
                try {
                    // 날짜 계산에서 가져온 MMdd 형식의 날짜 스트링을 자름
                    int Mondaym = Integer.parseInt(monthday[1].substring(0, 2));
                    int Mondayd = Integer.parseInt(monthday[1].substring(2, 4));

                    // 저장된 달과 월요일의 달이 같을경우
                    if(Mondaym == months)
                    {
                        // 이번달 급식 파싱
                        monday.setText(meals[Mondayd - 1].dinner);
                    }
                    else if(Mondaym > months)
                    {
                        // 다음달 급식 파싱
                        monday.setText(nmmeals[Mondayd - 1].dinner);
                    }
                    else
                    {
                        // 이전달 급식 파싱
                        monday.setText(bmmeals[Mondayd - 1].dinner);
                    }

                } catch (Exception e) {
                    // 에러 텍스트 설정
                    monday.setText(NoMeal);
                }

                // 화요일
                try {
                    int Tuesdaym = Integer.parseInt(monthday[2].substring(0, 2));
                    int Tuesdayd = Integer.parseInt(monthday[2].substring(2, 4));

                    if(Tuesdaym == months)
                    {
                        tuesday.setText(meals[Tuesdayd - 1].dinner);
                    }
                    else if(Tuesdaym > months)
                    {
                        // 다음달 급식 파싱
                        tuesday.setText(nmmeals[Tuesdayd - 1].dinner);
                    }
                    else
                    {
                        // 이전달 급식 파싱
                        tuesday.setText(bmmeals[Tuesdayd - 1].dinner);
                    }


                } catch (Exception e) {
                    tuesday.setText(NoMeal);
                }

                // 수요일
                try {
                    int Wednesdaym = Integer.parseInt(monthday[3].substring(0, 2));
                    int Wednesdayd = Integer.parseInt(monthday[3].substring(2, 4));

                    if(Wednesdaym == months)
                    {
                        wednesday.setText(meals[Wednesdayd - 1].dinner);
                    }
                    else if(Wednesdaym > months)
                    {
                        // 다음달 급식 파싱
                        wednesday.setText(nmmeals[Wednesdayd - 1].dinner);
                    }
                    else
                    {
                        // 이전달 급식 파싱
                        wednesday.setText(bmmeals[Wednesdayd - 1].dinner);
                    }

                } catch (Exception e) {
                    wednesday.setText(NoMeal);
                }

                try {
                    int Thursdaym = Integer.parseInt(monthday[4].substring(0, 2));
                    int Thursdayd = Integer.parseInt(monthday[4].substring(2, 4));

                    if(Thursdaym == months)
                    {
                        thursday.setText(meals[Thursdayd - 1].dinner);
                    }
                    else if(Thursdaym > months)
                    {
                        // 다음달 급식 파싱
                        thursday.setText(nmmeals[Thursdayd - 1].dinner);
                    }
                    else
                    {
                        // 이전달 급식 파싱
                        thursday.setText(bmmeals[Thursdayd - 1].dinner);
                    }

                } catch (Exception e) {
                    thursday.setText(NoMeal);
                }

                try {
                    int Fridaym = Integer.parseInt(monthday[5].substring(0, 2));
                    int Fridayd = Integer.parseInt(monthday[5].substring(2, 4));

                    if(Fridaym == months)
                    {
                        friday.setText(meals[Fridayd - 1].dinner);
                    }
                    else if(Fridaym > months)
                    {
                        // 다음달 급식 파싱
                        friday.setText(nmmeals[Fridayd - 1].dinner);
                    }
                    else
                    {
                        // 이전달 급식 파싱
                        friday.setText(bmmeals[Fridayd - 1].dinner);
                    }

                } catch (Exception e) {
                    friday.setText(NoMeal);
                }
            }
        }
        else
        {
            Fragment MainFrag = MainFragment.newInstance();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_activity_main, MainFrag).commit();
            Toast.makeText(getActivity(), "급식 데이터를 불러올 수 없습니다. 다운로드를 진행해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    public static String[] DateCalculator()
    {
        Calendar cal = Calendar.getInstance();
        List<String> DayList = new ArrayList<String>();
        DayList.add(new SimpleDateFormat("MMdd").format(cal.getTime()));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        for(int i=1; i < 7; i++) {
            cal.add(Calendar.DATE, 1);
            DayList.add(new SimpleDateFormat("MMdd").format(cal
                    .getTime()));
        }

        String[] DayListString = DayList.toArray(new String[DayList.size()]);

        return DayListString;
    }
}