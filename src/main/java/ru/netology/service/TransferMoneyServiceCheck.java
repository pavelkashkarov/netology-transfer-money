package ru.netology.service;

import ru.netology.dto.request.TransferRQ;
import ru.netology.exception.InputDataException;
import ru.netology.model.Card;

public class TransferMoneyServiceCheck {
    public final Card cardFrom;
    public final Card cardTo;

    public final String cardFromValidTill;
    public final String cardFromCVV;
    public final String transferRQCardFromValidTill;
    public final String transferRQCardFromCVV;

    public TransferMoneyServiceCheck(Card cardFrom, Card cardTo, String cardFromValidTill, String cardFromCVV,
                                     String transferRQCardFromValidTill, String transferRQCardFromCVV) {
        this.cardFrom = cardFrom;
        this.cardTo = cardTo;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.transferRQCardFromValidTill = transferRQCardFromValidTill;
        this.transferRQCardFromCVV = transferRQCardFromCVV;
    }

    public void checkCardsConsistency(){
        if (cardFrom == null && cardTo != null) {
            log.error("card from not found");
            throw new InputDataException("card from not found");
        } else if (cardFrom != null && cardTo == null) {
            log.error("card to not found");
            throw new InputDataException("card to not found");
        } else if (cardFrom == null && cardTo == null) {
            log.error("card from and card to not found");
            throw new InputDataException("card from and card to not found");
        }
        if (cardFrom == cardTo) {
            log.error("two identical card numbers");
            throw new InputDataException("two identical card numbers");
        }
    }

    public void checkCardValidation(TransferRQ transferRQ){
        if (!cardFromValidTill.equals(transferRQCardFromValidTill) && cardFromCVV.equals(transferRQCardFromCVV)) {
            log.error("card from invalid data: valid till");
            throw new InputDataException("card from invalid data: valid till");
        } else if (cardFromValidTill.equals(transferRQCardFromValidTill) && !cardFromCVV.equals(transferRQCardFromCVV)) {
            log.error("card from invalid data: cvv");
            throw new InputDataException("card from invalid data: cvv");
        } else if (!cardFromValidTill.equals(transferRQCardFromValidTill) && !cardFromCVV.equals(transferRQCardFromCVV)) {
            log.error("card from invalid data: valid till and cvv");
            throw new InputDataException("card from invalid data: valid till and cvv");
        }

        if (cardFrom.getAmount().getValue() < transferRQ.getAmount().getValue()) {
            log.error("card from invalid data: not enough money");
            throw new InputDataException("card from invalid data: not enough money");
        }
    }
}
