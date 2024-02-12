package ru.netology.service;

import ru.netology.dto.request.ConfirmRQ;
import ru.netology.dto.request.TransferRQ;
import ru.netology.dto.response.TransferAndConfirmRS;

public interface TransferMoneyService {
    TransferAndConfirmRS postConfirmOperation(ConfirmRQ confirmRQ);
    TransferAndConfirmRS postTransfer(TransferRQ transferRQ);
}
