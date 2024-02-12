package ru.netology.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.netology.dto.request.ConfirmRQ;
import ru.netology.dto.request.TransferRQ;
import ru.netology.dto.response.TransferAndConfirmRS;
import ru.netology.exception.InputDataException;
import ru.netology.model.Card;
import ru.netology.repository.TransferMoneyRepository;

@Service
@AllArgsConstructor
@Slf4j
public class TransferMoneyServiceImpl implements TransferMoneyService {

    private final TransferMoneyRepository repository;

    public TransferAndConfirmRS postTransfer(TransferRQ transferRQ) {
        final Card cardFrom = repository.getCard(transferRQ.getCardFromNumber());
        final Card cardTo = repository.getCard(transferRQ.getCardToNumber());
        final String cardFromValidTill = cardFrom.getValidTill();
        final String cardFromCVV = cardFrom.getCvv();
        final String transferRQCardFromValidTill = transferRQ.getCardFromValidTill();
        final String transferRQCardFromCVV = transferRQ.getCardFromCVV();

        TransferMoneyServiceCheck serviceCheck = new TransferMoneyServiceCheck(cardFrom, cardTo, cardFromValidTill,
                                                     cardFromCVV, transferRQCardFromValidTill, transferRQCardFromCVV);

        serviceCheck.checkCardsConsistency();
        serviceCheck.checkCardValidation(transferRQ);

        final String transferId = Integer.toString(repository.incrementAndGetOperationId());
        repository.putTransfers(transferId, transferRQ);
        repository.putCodes(transferId, "0000");

        return new TransferAndConfirmRS(transferId);
    }

    public TransferAndConfirmRS postConfirmOperation(ConfirmRQ confirmRQ) {
        final String operationId = confirmRQ.getOperationId();
        final TransferRQ transferRQ = repository.removeTransfer(operationId);

        if (transferRQ == null) {
            log.error("confirm operation invalid data: operation not found");
            throw new InputDataException("confirm operation invalid data: operation not found");
        }

        final String code = repository.removeCode(operationId);
        if (!confirmRQ.getCode().equals(code) || code.isEmpty()) {
            log.error("confirm operation invalid data: code");
            throw new InputDataException("confirm operation invalid data: code");
        }

        final Card cardFrom = repository.getCard(transferRQ.getCardFromNumber());
        final Card cardTo = repository.getCard(transferRQ.getCardToNumber());

        final int cardFromValue = cardFrom.getAmount().getValue();
        final int cardToValue = cardTo.getAmount().getValue();
        final int transferValue = transferRQ.getAmount().getValue();
        final int commission = (int) (transferValue * 0.01);

        cardFrom.getAmount().setValue(cardFromValue - transferValue);
        cardTo.getAmount().setValue(cardToValue + transferValue - commission);

        log.info(String.format("Success transfer. Transfer operation id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                operationId, transferRQ.getCardFromNumber(), transferRQ.getCardToNumber(), transferValue, commission));

        return new TransferAndConfirmRS(operationId);
    }
}