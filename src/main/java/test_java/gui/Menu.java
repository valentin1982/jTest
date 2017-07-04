package test_java.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import test_java.service.RazdelServise;
import test_java.service.TestService;

@Component
public class Menu
{
    @Qualifier("razdelServise")
    @Autowired
    private RazdelServise razdelServise;
    @Qualifier("loadWindow")
    @Autowired
    private LoadWindow loadWindow;
    @Qualifier("testService")
    @Autowired
    private TestService testService;
    @Autowired
    private RazdelServise showFrame;

    public TestService getTestService()
    {
        return this.testService;
    }

    public void setTestService(TestService testService)
    {
        this.testService = testService;
    }

    public RazdelServise getRazdelServise()
    {
        return this.razdelServise;
    }

    public void setRazdelServise(RazdelServise razdelServise)
    {
        this.razdelServise = razdelServise;
    }

    public LoadWindow getLoadWindow()
    {
        return this.loadWindow;
    }

    public void setLoadWindow(LoadWindow loadWindow)
    {
        this.loadWindow = loadWindow;
    }

    public void setShowFrame(RazdelServise showFrame)
    {
        this.showFrame = showFrame;
    }

    public RazdelServise getShowFrame()
    {
        return this.showFrame;
    }
}
