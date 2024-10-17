package clients;

import java.util.ArrayList;

import java.util.Comparator;

public class Members {
    private ArrayList<GymMember> memberList;

    public Members(ArrayList<GymMember> memberList) {
        this.memberList = memberList;
        this.updateMemberships();
        this.sortBySocial();
    }

    public GymMember getMember(int index) {
        return this.memberList.get(index);
    }

    public ArrayList<GymMember> getMemberList() {
        return memberList;
    }

    public void addMember(GymMember member) {
        memberList.add(member);
        sortBySocial();
    }

    public void updateMemberships() {
        for (GymMember m : this.memberList) {
            m.updateMembership();
        }
    }

    public boolean activeMember(Long socialSecurity) {
        int index = getMemberIndex(socialSecurity);
        if (index >= 0) {
            return this.memberList.get(index).activeMembership;
        }
        return false;
    }

    public boolean memberExist(Long socialSecurity) {
        return getMemberIndex(socialSecurity) >= 0;
    }

    public void sortBySocial() {
        this.memberList.sort(Comparator.comparingLong(m -> m.socialSecurity));
    }

    public ArrayList<GymMember> getMembersByName(String name) {
        ArrayList<GymMember> members = new ArrayList<>();
        for (GymMember gymMember : this.memberList) {
            if (gymMember.getName().equalsIgnoreCase(name)) {
                members.add(gymMember);
            }
        }
        return members;
    }

    public int getMemberIndex(Long socialSecurity) {
        int left = 0, right = this.memberList.size() - 1;
        int mid;
        long midSocial;

        while (left <= right) {
            mid = left + (right - left) / 2;
            midSocial = memberList.get(mid).getSocialSecurity();
            if (midSocial == socialSecurity) {
                return mid;
            } else if (midSocial < socialSecurity) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
