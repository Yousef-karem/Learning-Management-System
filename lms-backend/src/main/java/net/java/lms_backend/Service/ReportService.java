package net.java.lms_backend.Service;

import net.java.lms_backend.Repositrory.PerformanceRepo;
import net.java.lms_backend.Repositrory.QuizAttemptRepository;
import net.java.lms_backend.entity.Performance;
import net.java.lms_backend.entity.QuizAttempt;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportService {
    private final PerformanceRepo performanceRepo;
    private final QuizAttemptRepository quizAttemptRepo;
    public ReportService(PerformanceRepo performanceRepo, QuizAttemptRepository quizAttemptRepo) {
        this.performanceRepo = performanceRepo;
        this.quizAttemptRepo = quizAttemptRepo;
    }

    public ByteArrayInputStream createExcelReport() {
        List<Performance> PeformenceData = performanceRepo.findAll();
        List<QuizAttempt> data= quizAttemptRepo.findAll();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Performance");


            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Student Name");
            headerRow.createCell(1).setCellValue("Attendance");
            headerRow.createCell(2).setCellValue("Percentage");


            int rowIdx = 1;
            for (Performance sp : PeformenceData) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(sp.getStudent().getUsername());
                row.createCell(1).setCellValue(sp.getTotalLessonsAttended());
                float percentage=100;
                if(sp.getTotalLessons()!=0) {
                    percentage=(float) sp.getTotalLessonsAttended() / sp.getTotalLessons();
                    percentage*=100;
                }
                row.createCell(2).setCellValue(percentage +"%");
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel file", e);
        }
    }
}
