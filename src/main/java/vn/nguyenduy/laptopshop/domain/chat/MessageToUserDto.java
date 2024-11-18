package vn.nguyenduy.laptopshop.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageToUserDto {
    private String messageFromRes;
    private String messageToRes;
    private String messageTextRes;
}