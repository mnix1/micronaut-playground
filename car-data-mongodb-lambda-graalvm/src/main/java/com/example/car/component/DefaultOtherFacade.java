package com.example.car.component;

import com.example.car.api.OtherFacade;
import jakarta.inject.Singleton;

@Singleton
class DefaultOtherFacade implements OtherFacade {

    @Override
    public void somethingBeforeAudit() {}

    @Override
    public void somethingAfterAudit() {}
}
