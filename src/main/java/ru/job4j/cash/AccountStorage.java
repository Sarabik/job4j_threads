package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {

    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        Account acc = accounts.putIfAbsent(account.id(), account);
        return acc == null;
    }

    public synchronized boolean update(Account account) {
        boolean result = false;
        Optional<Account> optional = getById(account.id());
        if (optional.isPresent()) {
            result = accounts.replace(account.id(), optional.get(), account);
        } else {
            throw new IllegalArgumentException("Account is not found");
        }
        return result;
    }

    public synchronized boolean delete(int id) {
        boolean result = false;
        Optional<Account> optional = getById(id);
        if (optional.isPresent()) {
            result = accounts.remove(id, optional.get());
        } else {
            throw new IllegalArgumentException("Account is not found");
        }
        return result;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.getOrDefault(id, null));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        Optional<Account> optionalFrom = getById(fromId);
        Optional<Account> optionalTo = getById(toId);
        if (optionalFrom.isPresent() && optionalTo.isPresent()) {
            Account accFrom = optionalFrom.get();
            Account accTo = optionalTo.get();
            if (amount <= accFrom.amount()) {
                accounts.replace(accFrom.id(), accFrom, new Account(fromId, accFrom.amount() - amount));
                accounts.replace(accTo.id(), accTo, new Account(toId, accTo.amount() + amount));
                result = true;
            } else {
                throw new IllegalArgumentException("The debited amount is greater than the amount in the account");
            }
        } else {
            throw new IllegalArgumentException("One or both accounts are not found");
        }
        return result;
    }
}
