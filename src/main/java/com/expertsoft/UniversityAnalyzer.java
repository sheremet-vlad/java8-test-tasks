package com.expertsoft;

import com.expertsoft.model.Student;

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
}
