package me.kuykendall.restfest.hackday.dao;

import me.kuykendall.restfest.hackday.model.OperatingReactor;

public interface OperatingReactorDAO {
    OperatingReactor getOperatingReactorByDocketNumber(String docketNumber);
}
