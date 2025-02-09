package vstu.isd.notebin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserNotesRequestDto {
    private Long userId;
    private int page;
}
