package me.kuykendall.restfest.hackday.dao;

import me.kuykendall.restfest.hackday.OperatingReactorQueryInfo;
import me.kuykendall.restfest.hackday.model.OperatingReactor;

import java.util.List;

public interface OperatingReactorDAO {
    OperatingReactor getOperatingReactorByDocketNumber(String docketNumber);
    List<OperatingReactor> getOperatingReactors(OperatingReactorQueryInfo queryInfo);
}
