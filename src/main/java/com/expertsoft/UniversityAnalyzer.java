package com.expertsoft;

import com.expertsoft.model.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UniversityAnalyzer {
    /**
     * Should return min subject mark
     *
     * @param students  students stream
     * @param subjectId find min mark for subject with such id
     * @return
     */
    public OptionalInt getMinSubjectMark(Stream<Student> students, int subjectId) {
        return students
                .flatMap(student -> student.getSubjectMarks().stream())
                .filter(x -> x.getSubjectId() == subjectId)
                .mapToInt(SubjectMark::getMark)
                .min();
    }

    /**
     * Should return average mark given by teacher
     *
     * @param students  students stream
     * @param teacherId find average given mark for teacher with such id
     * @return
     */
    public OptionalDouble getAverageTeacherMark(Stream<Student> students, int teacherId) {
        return students
                .flatMap(student -> student.getSubjectMarks().stream())
                .filter(x -> x.getTeacherId() == teacherId)
                .mapToDouble(SubjectMark::getMark)
                .average();
    }

    /**
     * Should return min students age (years). Need to count the number of full years.
     *
     * @param students not empty students stream
     * @return
     */
    public Integer getMinStudentAgeInYears(Stream<Student> students) {
        return students
                .map(x -> Period.between(x.getBirthday(), LocalDate.now()).getYears())
                .mapToInt(x -> x)
                .min()
                .orElse(-1);
    }

    /**
     * Should return student with highest average mark.
     * If two or more students have the same average mark, then return any of this students.
     *
     * @param students not empty students stream
     * @return
     */
    public Student getStudentWithHighestAverageMark(Stream<Student> students) {
        return students
                .collect(Collectors.toMap(s -> s, s -> s
                        .getSubjectMarks()
                        .stream()
                        .mapToDouble(SubjectMark::getMark)
                        .average()))
                .entrySet()
                .stream()
                .max(Comparator.comparingDouble((e) -> e.getValue().orElse(0.0)))
                .orElseThrow().getKey();
    }

    /**
     * Return sorted students list.
     * If two students have the same count of marks, then students should be ordered by surname
     *
     * @param students students stream
     * @return
     */
    public List<Student> sortStudentsByCountOfMarks(Stream<Student> students) {
        return students
                .sorted((Comparator.comparing((Student x) -> x.getSubjectMarks().size()).reversed())
                        .thenComparing(Student::getSurname))
                .toList();
    }

    /**
     * Should return IDs of subjects sorted by academic performance in ascending order.
     *
     * @param students students stream
     * @return
     */
    public List<Integer> getSubjectsByAcademicPerformance(Stream<Student> students) {
        return students
                .flatMap(x -> x.getSubjectMarks().stream())
                .collect(Collectors.toMap(SubjectMark::getSubjectId, SubjectMark::getMark, Integer::sum))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * Should return the subject that most teachers lead.
     * If two or more subject lead the same count of teacher, then return any of this subjects.
     *
     * @param teachers not empty teachers stream
     * @return
     */
    public Subject getSubjectThatMostTeachersLead(Stream<Teacher> teachers) {
        return teachers
                .flatMap(t -> t.getTaughtSubjects().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .orElseThrow().getKey();
    }

    /**
     * Should sort excellent students by surname.
     * A student is considered an excellent student if his average mark is at least 8.
     * A student is considered a graduate if his age is not less than 21.
     *
     * @param students students stream
     * @return
     */
    public List<Student> getGraduatedExcellentStudents(Stream<Student> students) {
        return students
                .filter(x -> Period.between(x.getBirthday(), LocalDate.now()).getYears() >= 21)
                .filter(x -> x.getSubjectMarks().stream().mapToDouble(SubjectMark::getMark).average().orElse(0.0) >= 8)
                .toList();
    }

    /**
     * Should return the head of the a department whose students have the highest average mark.
     * If students from two or more departments have the same average mark, then return any head of this departments.
     *
     * @param departments not empty departments stream
     * @return
     */
    public Teacher getHeadOfTheMostSuccessfulDepartment(Stream<Department> departments) {
        return departments
                .collect(Collectors.toMap(Department::getHead, x -> x
                        .getStudents()
                        .stream()
                        .flatMap(s -> s
                                .getSubjectMarks()
                                .stream()
                        )
                        .mapToDouble(SubjectMark::getMark)
                        .average()
                        .orElse(0.0)
                ))
                .entrySet()
                .stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .orElseThrow()
                .getKey();
    }

    /**
     * Should return subjects list that head teaches in his department.
     *
     * @param department find corresponding subjects for this department
     * @return
     */
    public List<Subject> getSubjectsThatHeadTeachesInHisDepartment(Department department) {
        return department
                .getHead()
                .getTaughtSubjects()
                .stream()
                .filter(s -> department
                        .getSubjects()
                        .stream()
                        .mapToInt(Subject::getId)
                        .anyMatch(x -> x == s.getId()))
                .toList();
    }
}
