package comp3350.nilebookstore.tests;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import comp3350.nilebookstore.presentation.InitializeActivity;

public class LaunchTest
{
    @Rule
    public ActivityScenarioRule<InitializeActivity> activityRule = new ActivityScenarioRule<>(InitializeActivity.class);

    @Test
    public void launchTest()
    {

    }
}
