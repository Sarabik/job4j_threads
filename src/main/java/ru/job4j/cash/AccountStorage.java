package ru.job4j.cash;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountStorage {

    private final Map<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), getById(account.id()).get(), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id, getById(id).get());
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (getById(fromId).isPresent() && getById(toId).isPresent()) {
            Account accFrom = getById(fromId).get();
            Account accTo = getById(toId).get();
            if (amount <= accFrom.amount()) {
                accounts.replace(accFrom.id(), accFrom, new Account(fromId, accFrom.amount() - amount));
                accounts.replace(accTo.id(), accTo, new Account(toId, accTo.amount() + amount));
                result = true;
            }
        }
        return result;
    }
}
