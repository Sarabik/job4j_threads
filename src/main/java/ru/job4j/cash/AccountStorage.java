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
        Optional<Account> accFrom = getById(fromId);
        Optional<Account> accTo = getById(toId);
        if (accFrom.isPresent() && accTo.isPresent() && amount <= accFrom.get().amount()) {
            accounts.replace(accFrom.get().id(), accFrom.get(), new Account(fromId, accFrom.get().amount() - amount));
            accounts.replace(accTo.get().id(), accTo.get(), new Account(toId, accTo.get().amount() + amount));
            result = true;
        }
        return result;
    }
}
