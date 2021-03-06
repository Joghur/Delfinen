/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delfinen.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martin Wulff
 */
public class Accountant {

    private final List<Subscription> Budget;
    private final List<Member> Members;
    private final int MissingPayments;
    private List<Member> Debitors;
    private final float Bank;
    private final float ExpectedBank;

    /**
     * Constructor for an Accountant. Takes a list of subscriptions and list of
     * members and calculates all required information a book keeper need.
     *
     * @param Budget List of all subscriptions to be taken into consideration.
     * Should be for same period of time.
     * @param Members List of all current members. Used to find debitors and
     * calculate expected revenue.
     */
    public Accountant(List<Subscription> Budget, List<Member> Members) {
        this.Budget = Budget;
        this.Members = Members;
        this.MissingPayments = Members.size() - Budget.size();
        this.Bank = calcBudget();
        this.Debitors = Restance();
        switch (MissingPayments) {
            case 0:
                this.ExpectedBank = this.Bank;
                break;
            default:
                this.ExpectedBank = this.Bank + calcExpectedBank();
        }
    }

    /**
     * Method for finding how mutch is in the bank at the moment. Simple sum
     * function.
     *
     * @return Float. Sum of budget.price.
     */
    private float calcBudget() {
        float runsum = 0;
        for (Subscription subscription : Budget) {
            runsum += subscription.getPrice();
        }
        return runsum;
    }

    /**
     * Method for calculating Expected budget, when every member pays their
     * bills. Simple sum function.
     *
     * @return Sum of all Debitors supposed subscription.price
     */
    private float calcExpectedBank() {
        float runsum = 0;
        for (Member Debitor : Debitors) {
            if (Debitor != null) { // added check for nulldom
                runsum += new Subscription(0, Debitor).getPrice();
            }
        }
        return runsum;
    }

    /**
     * Method for finding all members without a paid subscription
     *
     * @return ArrayList. contains members without a subscription.
     */
    private List<Member> Restance() {
        List<Member> Out = new ArrayList<>();
        boolean flag;
        for (int i = 1; i < Members.size()-1; i++) {
            flag = true;
            for (int j = 0; j < Budget.size(); j++) {
                if (Members.get(i).equals(Budget.get(j).getHolder())) {
                    flag = false;
                }
            }
            if (flag) {
                Out.add(Members.get(i));
            }
        }
        return Out;
    }

    /* Den her metode tro åbenbart den er skrevet i C..... ihvertfald i JUnit Tests.
    private List<Member> Restance() {
        List<Member> temp = Members;
        for (Subscription Sub : Budget) {
            Member TM = Sub.getHolder();            
            if (temp.contains(TM)) {
                temp.remove(TM);                
            }
        }
        return temp;
    }
     */

    ///////////////////////      GETTERS AND SETTERS       /////////////////////
    ////////////////////////////////////////////////////////////////////////////    
    public int getMissingPayments() {
        return MissingPayments;
    }

    public List<Member> getDebitors() {
        return Debitors;
    }

    public float getBank() {
        return Bank;
    }

    public float getExpectedBank() {
        return ExpectedBank;
    }
}
