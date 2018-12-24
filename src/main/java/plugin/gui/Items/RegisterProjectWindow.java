package plugin.gui.Items;

import plugin.core.course.Course;
import plugin.core.eventbus.InformersImpl.CreateInformer;
import plugin.core.eventbus.InformersImpl.GetInformer;
import plugin.gui.KotoedContext;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static plugin.gui.Utils.PsiKeys.*;
import static plugin.gui.Utils.Strings.CONFIGURATION;
import static plugin.gui.Utils.Strings.OPEN;

public class RegisterProjectWindow extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField projectName;
    private JTextField repoUrl;
    private JRadioButton gitRadioButton;
    private JRadioButton mercurialRadioButton;
    private JComboBox courseList;
    private JLabel courseLabel;
    private ButtonGroup group;
    private boolean status = false;
    private List<Course> courses;

    public RegisterProjectWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e ->
                        onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.gitRadioButton.setActionCommand("git");
        this.mercurialRadioButton.setActionCommand("mercurial");

        this.group = ((DefaultButtonModel) mercurialRadioButton.getModel()).getGroup();

        GetInformer informer = new GetInformer(
                CONFIGURATION,
                Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_HEADERS)));
        courses = informer.getCourses();

        courses.forEach(course -> {
            if (course.getState().equals(OPEN))
                //noinspection unchecked
                courseList.addItem(course.getName());
        });

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.repoUrl.setText(KotoedContext.project.getUserData(PSI_KEY_REPO_URL));
    }

    private void onOK() {
        try {
            String repoType = this.group.getSelection().getActionCommand();
            String projectName = this.projectName.getText();
            String projectURL = this.repoUrl.getText();
            Course selectedCourse = null;

            for (Course course : courses)
                if (course.getName().equals(courseList.getSelectedItem()))
                    selectedCourse = course;


            CreateInformer informer = new CreateInformer(
                    CONFIGURATION,
                    Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_HEADERS)));

            // TODO РАБОТАЕТ, НО НЕ БУДЕМ ЗАСОРЯТЬ КОТОЕД :)
            /*informer.createProject(projectName,
                    Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_DENIZEN_ID)),
                    Objects.requireNonNull(selectedCourse).getId(),
                    repoType,
                    projectURL);*/

            dispose();
            status = true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Project registration error occurred, please try again.",
                    "Project registration",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
        status = false;
    }

    public boolean getStatus() {
        return status;
    }
}
