package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.text.Position;
import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job requestedJob = jobData.findById(id);
        model.addAttribute("job", requestedJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            return "new-job";

        }

        Employer ouremployer = null;
        Location ourlocation = null;
        PositionType ourposition = null;
        CoreCompetency ourcorecomp = null;






        for ( Employer employer : jobData.getEmployers().findAll()) {
            if (jobForm.getEmployerId() == employer.getId())
                    ouremployer = employer;
        }

        for ( Location location : jobData.getLocations().findAll()) {
            if (jobForm.getLocationId() == location.getId())
                ourlocation = location;
        }

        for ( PositionType position : jobData.getPositionTypes().findAll()) {
            if (jobForm.getPositionTypeId() == position.getId())
                ourposition = position;
        }

        for (CoreCompetency corecompetency : jobData.getCoreCompetencies().findAll()) {
            if (jobForm.getCoreCompetencyId() == corecompetency.getId())
                ourcorecomp = corecompetency;
        }

        Job newJob = new Job(jobForm.getName(),
        ouremployer, ourlocation, ourposition, ourcorecomp);



        jobData.add(newJob);
        model.addAttribute(newJob);

        return "job-detail";

    }
}
