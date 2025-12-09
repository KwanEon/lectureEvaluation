package egovframework.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
	private Long id;
	private Long userId;
	private Long evaluationId;
	private String reportTitle;
    private String reportContent;
    private String status;
}
