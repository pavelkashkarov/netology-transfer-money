package ru.netology.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.netology.model.Amount;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class TransferRQ {

    @NotBlank(message = "card from number must not be null")
    @Size(min = 16, max = 16)
    @Pattern(regexp = "(?<!\\d)\\d{16}(?!\\d)")
    private String cardFromNumber;

    @NotBlank(message = "card from valid till must not be null")
    @Size(min = 5, max = 5)
    @Pattern(regexp = "(0[1-9]|1[0-2])[/][0-9]{2}")
    private String cardFromValidTill;

    @NotBlank(message = "card from cvv must not be null")
    @Size(min = 3, max = 3)
    @Pattern(regexp = "(?<!\\d)\\d{3}(?!\\d)")
    private String cardFromCVV;

    @NotBlank(message = "card to number must not be null")
    @Size(min = 16, max = 16)
    @Pattern(regexp = "(?<!\\d)\\d{16}(?!\\d)")
    private String cardToNumber;

    private Amount amount;
}