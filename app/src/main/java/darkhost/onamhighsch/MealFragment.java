package darkhost.onamhighsch;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 민재 on 2016-03-07.
 */
public class MealFragment extends Fragment {
    public static Context Contexts;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    int pages;
    CharSequence Titles[]={ "점심", "저녁" };
    int Numboftabs = 2;

    public MealFragment() {

    }

    public static MealFragment newInstance(int page) {
        MealFragment fragment = new MealFragment();
        fragment.pages = page; // 0부터 시작
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(this.getChildFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        pager.setOffscreenPageLimit(2);

        // 지정된 페이지로 설정
        pager.setCurrentItem(pages);

        // 알레르기 메뉴 보이기
        MenuShow(true);

        return view;
    }

    public void onDestroyView() {
        // 알레르기 메뉴 숨기기
        MenuShow(false);
        super.onDestroyView();
    }

    // 알레르기 메뉴 활성화 / 비활성화 함수
    public void MenuShow(boolean ShowMenu) {
        if(MainActivity.menu == null)
            return;
        MainActivity.menu.setGroupVisible(R.id.algroup, ShowMenu);
    }
}
