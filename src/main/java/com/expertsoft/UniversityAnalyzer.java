package com.expertsoft;

import com.expertsoft.model.*;

import java.util.List;
import java.util.stream.Stream;

public class UniversityAnalyzer {
    /**
     * Should return min subject mark
     *
     * @param students  students stream
     * @param subjectId find min mark for subject with such id
     * @return
     */
    public int getMinSubjectMark(Stream<Student> students, int subjectId) {
        //TODO
        return 0;
    }

    /**
     * Should return average mark given by teacher
     *
     * @param students  students stream
     * @param teacherId find average given mark for teacher with such id
     * @return
     */
    public Double getAverageTeacherMark(Stream<Student> students, int teacherId) {
        //TODO
        return null;
    }

    /**
     * Should return average students age (years). Need to count the number of full years
     *
     * @param students students stream
     * @return
     */
    public double getAverageStudentAgeInYears(Stream<Student> students) {
        //TODO
        return 0.0;
    }

    /**
     * Should return student with highest average mark.
     *
     * @param students students stream
     * @return
     */
    public Student getStudentWithHighestAverageMark(Stream<Student> students) {
        //TODO
        return null;
    }

    /**
     * Return sorted students list.
     * If two students have the same count of marks, then students should be ordered by surname
     *
     * @param students students stream
     * @return
     */
    public List<Student> sortStudentsByCountOfMarks(Stream<Student> students) {
        //TODO
        return null;
    }

    /**
     * Should return IDs of subjects sorted by academic performance in ascending order.
     *
     * @param students students stream
     * @return
     */
    public List<Integer> getSubjectsByAcademicPerformance(Stream<Student> students) {
        //TODO
        return null;
    }

    /**
     * Should return the subject that most teachers lead.
     *
     * @param teachers teachers stream
     * @return
     */
    public Subject getSubjectThatMostTeachersLead(Stream<Teacher> teachers) {
        //TODO
        return null;
    }

    /**
     * Should return sorted students list.
     * A student is considered an excellent student if his average mark is at least 8.
     * A student is considered a graduate if his age is not less than 21.
     *
     * @param students students stream
     * @return
     */
    public List<Student> getGraduatedExcellentStudents(Stream<Student> students) {
        //TODO
        return null;
    }

    /**
     * Should return the head of the a department whose students have the highest average mark.
     *
     * @param departments departments stream
     * @return
     */
    public Teacher getHeadOfTheMostSuccessfulDepartment(Stream<Department> departments) {
        //TODO
        return null;
    }

    /**
     * Should return subjects list that head teaches in his department.
     *
     * @param department find corresponding subjects for this department
     * @return
     */
    public List<Subject> getSubjectsThatHeadTeachesInHisDepartment(Department department) {
        //TODO
        return null;
    }
}
