package wairoadc.godiscover;

import android.test.AndroidTestCase;

import java.util.List;

import wairoadc.godiscover.controller.ResourceController;
import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.model.Type;

/**
 * Created by Lucas on 10/05/2015.
 */
public class ResourceControllerTests extends AndroidTestCase {

    /**
     * Test scenario:
     * Name of the Track used for testing: Walk Home
     * Number of total spots: 5
     * Number of unlocked spots: 2. Spots 1 and 4
     * Number of total pictures: 9
     * Number of unlocked pictures: 4
     * @throws Throwable
     */
    public void testLoadAllUnlockedRes() throws Throwable{
        //Getting necessary information
        TrackController trackController = new TrackController(getContext());
        Track testTrack = new Track();
        testTrack.setName("Walk Home");
        testTrack = trackController.loadTrack(testTrack);

        //Invoking method to be tested
        ResourceController resourceController = new ResourceController(getContext());
        List<Resource> resources = resourceController.loadAllByType(testTrack,new Type(1),true);
        int numberUnlockedResources = resourceController.countResoucesByType(testTrack,new Type(1),true);
        //Validating results;
        assertTrue("List should not be null", resources != null);
        assertTrue("Unlocked spots for 'Walk Home' should be 4",numberUnlockedResources == 4);
        assertTrue("List should have 9 objects of type Resource",resources.size() == 9);
        Resource testResource = resources.get(0);
        assertTrue("Image path different from expected: /Walk Home/res/image/WalkHomeICenter.png",testResource.getPath().equals("/Walk Home/res/image/WalkHomeICenter.png"));
        testResource = resources.get(1);
        assertTrue("Image path different from expected: /Walk Home/res/image/WalkHomeWrightPrice.png",testResource.getPath().equals("/Walk Home/res/image/WalkHomeWrightPrice.png"));
        testResource = resources.get(2);
        assertNull("Image path different from expected: null",testResource);
        testResource = resources.get(3);
        assertNull("Image path different from expected: null",testResource);
        testResource = resources.get(4);
        assertNull("Image path different from expected: null",testResource);

        testResource = resources.get(5);
        assertTrue("Image path different from expected: /Walk Home/res/image/WalkHomeRobbies.png",testResource.getPath().equals("/Walk Home/res/image/WalkHomeRobbies.png"));
        testResource = resources.get(6);
        assertTrue("Image path different from expected: /Walk Home/res/image/WalkHomeRuss.png",testResource.getPath().equals("/Walk Home/res/image/WalkHomeRuss.png"));

        testResource = resources.get(7);
        assertNull("Image path different from expected: null",testResource);
        testResource = resources.get(8);
        assertNull("Image path different from expected: null",testResource);
    }

}
