package ru.netology.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.dto.request.ConfirmRQ;
import ru.netology.dto.request.TransferRQ;
import ru.netology.dto.response.TransferAndConfirmRS;
import ru.netology.service.TransferMoneyServiceImpl;

@RestController
@RequiredArgsConstructor
public class TransferMoneyController {

    private final TransferMoneyServiceImpl service;

    @PostMapping("/transfer")
    public TransferAndConfirmRS postTransfer(@RequestBody TransferRQ transferRequest) {
        return service.postTransfer(transferRequest);
    }

    @PostMapping("/confirmOperation")
    public TransferAndConfirmRS postConfirmOperation(@RequestBody ConfirmRQ confirmRequest) {
        return service.postConfirmOperation(confirmRequest);
    }
}