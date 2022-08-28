package com.expertsoft;

import com.expertsoft.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
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
        return students.flatMap(student -> student.getSubjectMarks().stream())
                .filter(subjectMark -> subjectMark.getSubjectId() == subjectId)
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
        return students.flatMap(student -> student.getSubjectMarks().stream())
                .filter(subjectMark -> subjectMark.getTeacherId() == teacherId)
                .mapToInt(SubjectMark::getMark).average();
    }

    /**
     * Should return min students age (years). Need to count the number of full years.
     *
     * @param students not empty students stream
     * @return
     */
    public Integer getMinStudentAgeInYears(Stream<Student> students) {
        return students.map(Student::getBirthday)
                .mapToInt(LocalDate::getYear)
                .map(year -> LocalDate.now().getYear() - year)
                .min().orElseThrow(NullPointerException::new);
    }

    /**
     * Should return student with highest average mark.
     * If two or more students have the same average mark, then return any of this students.
     *
     * @param students not empty students stream
     * @return
     */
    public Student getStudentWithHighestAverageMark(Stream<Student> students) {
        return students.collect(Collectors.toMap(Function.identity(), student -> student.getSubjectMarks().stream()
                        .map(SubjectMark::getMark)
                        .reduce(Integer::sum).get().doubleValue() / student.getSubjectMarks().size()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()).get().getKey();
    }

    /**
     * Return sorted students list.
     * If two students have the same count of marks, then students should be ordered by surname
     *
     * @param students students stream
     * @return
     */
    public List<Student> sortStudentsByCountOfMarks(Stream<Student> students) {
        return students.sorted(Comparator.comparing((Student student) -> student.getSubjectMarks().size()).reversed()
                        .thenComparing(Student::getSurname))
                .collect(Collectors.toList());
    }

    /**
     * Should return IDs of subjects sorted by academic performance in ascending order.
     *
     * @param students students stream
     * @return
     */
    public List<Integer> getSubjectsByAcademicPerformance(Stream<Student> students) {
        return students.flatMap(student -> student.getSubjectMarks().stream())
                .collect(Collectors.groupingBy(SubjectMark::getSubjectId, Collectors.averagingDouble(SubjectMark::getMark)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Should return the subject that most teachers lead.
     * If two or more subject lead the same count of teacher, then return any of this subjects.
     *
     * @param teachers not empty teachers stream
     * @return
     */
    public Subject getSubjectThatMostTeachersLead(Stream<Teacher> teachers) {
        return teachers.collect(Collectors.toMap(Function.identity(), Teacher::getTaughtSubjects))
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();
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
        return students.collect(Collectors.toMap(Function.identity(), Student::getBirthday)).entrySet()
                .stream()
                .filter(entry -> LocalDate.now().getYear() - entry.getValue().getYear() >= 21)
                .map(Map.Entry::getKey)
                .collect(Collectors.toMap(Function.identity(), student -> student.getSubjectMarks().stream()
                        .map(SubjectMark::getMark)
                        .reduce(Integer::sum).get().doubleValue() / student.getSubjectMarks().size()))
                .entrySet()
                .stream()
                .filter(studentDoubleEntry -> studentDoubleEntry.getValue() >= 8)
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(Student::getSurname))
                .collect(Collectors.toList());
    }

    /**
     * Should return the head of the a department whose students have the highest average mark.
     * If students from two or more departments have the same average mark, then return any head of this departments.
     *
     * @param departments not empty departments stream
     * @return
     */
    public Teacher getHeadOfTheMostSuccessfulDepartment(Stream<Department> departments) {
        return departments.collect(Collectors.toMap(Function.identity(), Department::getStudents))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
                        .collect(Collectors.toMap(Function.identity(), student -> student.getSubjectMarks().stream()
                                .map(SubjectMark::getMark)
                                .reduce(Integer::sum).get().doubleValue() / student.getSubjectMarks().size()))
                        .entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue)).get()))
                .entrySet()
                .stream()
                .max(Comparator.comparingDouble(entry -> entry.getValue().getValue())).get().getKey().getHead();
    }

    /**
     * Should return subjects list that head teaches in his department.
     *
     * @param department find corresponding subjects for this department
     * @return
     */
    public List<Subject> getSubjectsThatHeadTeachesInHisDepartment(Department department) {
        List<Integer> subjects = department.getSubjects().stream().map(Subject::getId).collect(Collectors.toList());
        return department.getHead().getTaughtSubjects()
                .stream()
                .filter(subject -> subjects.contains(subject.getId()))
                .collect(Collectors.toList());
    }
}
