package vn.nguyenduy.laptopshop.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageFromProducerDto {
    private String messageFrom;
    private String messageTo;
    private String messageText;
}
