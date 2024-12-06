package com.javaproject.venbakural;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DFA {
    // Define states as constants
    private static final int Q0 = 0;
    private static final int Q1 = 1;
    private static final int Q3 = 3; // Final state
    private static final int Q4 = 4; // Final state
    private static final int Q5 = 5;
    private static final int Q6 = 6;
    private static final int Q7 = 7;
    private static final int Q8 = 8; // Final state

    // Define final states
    private static final int[] FINAL_STATES = {Q3,Q1};

    // Static transition table: states × inputs (0, 1)
    // Columns: [0] for input '0', [1] for input '1'
    private static final int[][] TRANSITION_TABLE = {
            /* State Q0 */ {Q1, Q1},       // Transitions for Q0
            /* State Q1 */ {Q3, Q4},       // Transitions for Q1
            /* No Q2 */    {0, 0},         // Placeholder (no Q2 defined)
            /* State Q3 */ {Q7, 0},        // Q3: '0' to Q7, no '1' transition
            /* State Q4 */ {Q7, 0},        // Q4: '0' to Q7, no '1' transition
            /* State Q5 */ {0, Q1},        // Q5: '1' to Q1
            /* State Q6 */ {Q1, 0},        // Q6: '0' to Q1
            /* State Q7 */ {Q8, 0},        // Q7: '-1' to Q8 handled in code
            /* State Q8 */ {Q1, 0}         // Q8: '0' to Q1
    };

    // Check if the current state is a final state
    private static boolean isFinalState(int state) {
        for (int finalState : FINAL_STATES) {
            if (state == finalState) {
                return true;
            }
        }
        return false;
    }




    // Process input array and determine if it’s accepted by the DFA
    public static boolean venbaDFA(List<String> nerniraiList) {
        ArrayList<Integer> pattern = new ArrayList<>();

        for (String str : nerniraiList) {
            for (char ch : str.toCharArray()) {
                pattern.add(Character.getNumericValue(ch)); // Convert character to numeric value
            }
            pattern.add(-1); // Add -1 after processing each string
        }

        pattern.remove(pattern.size() - 1);




        int currentState = Q0; // Start from the initial state

        for (int value : pattern) {
            if (value == -1) {
                // Custom handling for '-1' transitions
                if (currentState == Q3) {
                    currentState = Q5;
                } else if (currentState == Q4) {
                    currentState = Q6;
                } else if (currentState == Q7) {
                    currentState = Q8;
                } else {
                    // No valid transition for '-1'
                    return false;
                }
            } else if (value == 0 || value == 1) {
                // Standard transitions for '0' and '1'
                currentState = TRANSITION_TABLE[currentState][value];
            } else {
                // Invalid input
                Log.e(TAG, "Invalid input value: " + value);
                return false;
            }
        }

        return isFinalState(currentState); // Check if the final state is reached
    }

}