package ee.msaareva.decathlon.entity;

import lombok.Data;

@Data
public class ResultDto {
    public Long id;
    public String discipline;
    public Double value;
    public Double score;
    public Long competitorId;
}
